# EditText在ListView中无焦点、无光标，终极解决方案
在ListView中使用EditText作为Item时，EditText无法获取焦点，即使获取焦点光标也不闪烁，而且滚动ListView之后焦点还会消失。
## 1.终极解决方案
* layout_item_view.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="New Text" />

    <EditText
        android:id="@+id/editText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="numberDecimal"/>
</LinearLayout>
```
* [com.laole918.edittextinlistview.adapter.ListViewAdapter#onBindViewHolder()](https://github.com/laole918/EditTextInListView/blob/master/app/src/main/java/com/laole918/edittextinlistview/adapter/ListViewAdapter.java#L68)

```java
    public void onBindViewHolder(final ListViewHolder holder, final int position) {
        final User u = us.get(position);
        // 第1步：移除在第6步添加的TextWatcher，确保在第2步TextWatcher不会触发;
        // 
        // 提示：当EidtText的内容发生改变时，TextWatcher就会被触发。
        //       
        //       EditText是用ArrayList<TextWatcher>来存储的监听器的,所以必须保证
        //       在这个list里只有一个TextWatcher；
        // 
        // 为了避免TextWatcher在第3步被调用，提前将他移除。
        // 
        if (holder.editText.getTag() instanceof TextWatcher) {
            holder.editText.removeTextChangedListener((TextWatcher) (holder.editText.getTag()));
        }
        // 第2步：设置OnFocusChangeListener当EditText获取焦点后重新设置光标visible为true。
        //
        // 确保只有获取到焦点的view的CursorVisible为true。
        holder.editText.setCursorVisible(false);
        holder.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    holder.editText.setCursorVisible(true);
                } else {
                    holder.editText.setCursorVisible(false);
                }
            }
        });
        holder.textView.setText(u.getName());
        String phone = u.getPhone();
        // 第3步：移除TextWatcher之后，设置EditText的Text。
        if(TextUtils.isEmpty(phone)) {
            holder.editText.setText("");
        } else {
            holder.editText.setText(phone);
        }
        // 设置焦点跟光标状态
        //
        // 提示：ListView有一套非常好的缓存复用机制，所以在ListView中的View状态是不可靠。
        //       特别是EditText作为ListView的Item时，软键盘的弹出及隐藏，会导致ListView重新布局，
        //       多次调用adapter's getView()。
        //       如果其中一个EditText获取焦点，当它在其他位置复用时候，焦点就会混乱。 
        //       
        //       所有使用数据源来控制焦点是解决这个问题的核心。      
        if(selectPosition == position) {
            if(!holder.editText.isFocused()) {
                holder.editText.requestFocus();
            }
            CharSequence text = holder.editText.getText();
            holder.editText.setSelection(TextUtils.isEmpty(text) ? 0 : text.length());
            // 使光标闪烁
            holder.editText.setCursorVisible(true);
        } else {
            if(holder.editText.isFocused()) {
                holder.editText.clearFocus();
            }
            holder.editText.setCursorVisible(false);
        }
        // 第4步：给EditText设置一个OnTouchListener来更新它的焦点状态，记录到数据源。
        // 
        // 在第3步中我们了解到必须要从数据源来控制View的状态，所以我们用OnTouchListener来监听
        // ACTION_UP事件，来更新View的状态记录到数据源。
        // 我们并不想在这里消耗触摸事件，所以给onTouch()返回false。
        holder.editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(selectPosition != position && !holder.editText.isFocused()) {
                        holder.editText.requestFocus();
                    }
                    selectPosition = position;
                }
                return false;
            }
        });
        // 第5步：给EditText添加一个TextWatcher来监听它的Text变化，更新到数据源
        // 
        // 我们用数据源来控制View的状态。
        // 当用户编辑其中一个EditText之后并且滚动ListView。这个EditText将在不确定的位置被重用。
        // 用户输入的内容跟焦点一样会混乱。
        // 那怎么解决这个问题呢？
        // 简单，我们只要在用户编辑完成之后将内容保存到数据源。TextWatcher就可以很好的解决这个问题。
        final TextWatcher watcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    u.setPhone(null);
                } else {
                    u.setPhone(String.valueOf(s));
                }
            }
        };
        holder.editText.addTextChangedListener(watcher);
        // 第6步：将TextWatcher设置成EditText的Tag。
        // 这样就能在第4步将它移除。
        // 确保EditText中只有一个TextWatcher产生回调。    
        holder.editText.setTag(watcher);
    }
```
## 2.最佳解决方案RecyclerView
RecyclerView是谷歌V7包下新增的控件，用来替代ListView的使用，在RecyclerView标准化了ViewHolder类似于ListView中convertView用来做视图缓。</br>
[com.laole918.edittextinlistview.adapter.RecyclerViewAdapter#onBindViewHolder()](https://github.com/laole918/EditTextInListView/blob/master/app/src/main/java/com/laole918/edittextinlistview/adapter/RecyclerViewAdapter.java#L47)
```java
    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        if (holder.editText.getTag() instanceof TextWatcher) {
            holder.editText.removeTextChangedListener((TextWatcher) (holder.editText.getTag()));
        }
        final User u = us.get(position);
        holder.textView.setText(u.getName());
        String phone = u.getPhone();
        if(TextUtils.isEmpty(phone)) {
            holder.editText.setText("");
        } else {
            holder.editText.setText(phone);
        }
        if(selectPosition == position) {
            if(!holder.editText.isFocused()) {
                holder.editText.requestFocus();
            }
            CharSequence text = holder.editText.getText();
            holder.editText.setSelection(TextUtils.isEmpty(text) ? 0 : text.length());
        } else {
            if(holder.editText.isFocused()) {
                holder.editText.clearFocus();
            }
        }
        holder.editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    selectPosition = position;
                }
                return false;
            }
        });
        final TextWatcher watcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    u.setPhone(null);
                } else {
                    u.setPhone(String.valueOf(s));
                }
            }
        };
        holder.editText.addTextChangedListener(watcher);
        holder.editText.setTag(watcher);
    }
```
## 3.感谢
[https://github.com/Aspsine/EditTextInListView](https://github.com/Aspsine/EditTextInListView "Aspsine") 整个项目都是参照了大神的项目，发现有光标闪烁问题，就添加了第6步</br>
[RecyclerView使用详解（一）](http://frank-zhu.github.io/android/2015/01/16/android-recyclerview-part-1/ "Frank-Zhu")</br>
[RecyclerView使用详解（二）](http://frank-zhu.github.io/android/2015/02/25/android-recyclerview-part-2/ "Frank-Zhu")
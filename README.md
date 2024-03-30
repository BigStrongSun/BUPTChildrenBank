# BUPTChildrenBank
This is CourseWork of EBU6304 Software Engineering 
- 我才发现如果把这个clone下来是没有lib的，如果没有可以在src的同级目录下创建文件夹lib, 把projectRamen中的lib的fastjson-1.2.68.jar copy进来。然后再把整个lib Add as library就可以了。

- BtnGray和BtnOrange定义好了橙色和灰色的按钮样式，可直接使用。
  - 使用方法：JButton createTaskButton = new BtnOrange("BtnName");

- IOController和JSONController是之间学长学姐写的，直接拿过来用了。
  - 用来读取json格式的txt文件数据，支持读取单列表{}和数组[{},{},{}]

- PageSwitcher是用来实现页面跳转的工具类，不能传参。
  - 使用方法：PageSwitcher.switchPages(frameToClose, new frameToOpen);

- TopPanel是写好的标题栏，包含返回按钮和标题名称。
  - 使用方法：TopPanel topPanel = new TopPanel("title", frameToClose, new frameToOpen);

- TimePicker是时间选择器，仅仅展示弹窗页面，如需让选择的时间显示在另一页面上，需调用TimeSelectionListener接口。
  - 使用方法：TimePicker timePicker = new TimePicker();


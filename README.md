##sbdemo

####快捷键
ctrl+alt+左 回到上一步

ctrl+alt+右 回到下一步

ctrl+shift+/ 块注释

ctrl+/ 行注释

alt+n import一些类或者添加catch等系统自动生成的东西

alt+insert 自动创建get/set 选择get and set

ctrl+n 搜索class等文件(报错的文件加行数也可以直接搜索定位)

ctrl+shift+n 搜索file等文件

ctrl+alt+v 自动获取变量（把一条分成两条 先定义在使用）

shift+f6 更改变量名（code 只修改代码 all 所有同名的字符串都会更改 一般用code模式）

shift+回车 直接到下一行

ctrl+p 查看方法所需的参数

ctrl+w 选中上一层 在html文件中很好用可以选中标签内全部内容

shift+f6 重命名

ctrl+f12 展示该文件下所有方法列表 可以粘贴方法名字定位方法

html标签名+tab 快速生成html标签

####小操作
- 代码右键 git show history 双击记录可以看到记录
- 误判出错时 alt+enter 选择inspection disable 可以忽略这个出错标记
（比如由于Mybatis的启动器并不是Spring官方出的，所以Spring会认为UserMapper对象没有被注入容器，从而报错）
- 改错代码时可以使用 右键-git-revert 还原代码

####mybatis generator 操作SQL使用说明
class指User Question等类的实例 example值要作用的数据库的位置
example.createCriteria().and列名EqualTo(question.get列名());把某列的值定义为具体值 方便后续查找
- 新增
  + insert(class)
- 修改
  + updateByExampleSelective(class, example)
- 删除
  + 
- 查找
  + selectByPrimaryKey(class.id) 根据主键的值查找
  + selectByExample(example) 根据example内容查找
## factorymethod 工厂方法

根据不同的factory 实例来确定不同的Worker对象，这样可以保证Worker对象在你需要的时候，再有工厂为你提供合适Worker来工作

不好的地方：需要你提前确定好，是什么Worker在什么工厂里，或者为你的工厂提供选择worker的决策信息

## AbstractFactory 抽象工厂

个人没看出和factorymethod的区别

## builder 建造者模式
 
 是将一个复杂的对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。

理解： builder为系统的构建，类比下对象的构造方法，可以根据传入不同的builder来生成不同的实例

## singleton 单态模式

保证一个服务实例

## prototype 原型模式

通过cloneable接口，实现每次一个新的实例服务。
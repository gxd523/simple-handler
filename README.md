# simple-handler
简单实现Handler运行机制

Looper.prepare();

Message msg = Message.obtain();
handler.sendMessageDelayed(msg);
messageQueue.enqueueMessage(msg);
// 将Message加入MessageQueue时，会往管道写入字符，会唤醒loop线程；
// 如果MessageQueue中没有Message，并处于Idle状态，则会执行IdelHandler接口中的方法，往往用于做一些清理性地工作。

Looper.loop();
messageQueue.next();
msg.target.dispatchMessage(msg);
target.handleMessage(msg);
msg.recycle();
![](handler_arch.png)
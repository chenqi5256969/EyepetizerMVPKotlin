# EyepetizerMVPKotlin
## 模仿开眼视频的kotlin项目，采用单Activity+Fragment的设计，利用dagger优雅的实现mvp架构

##### 1.网络缓存+断网，出错自动重连（10次） 
##### 利用rxjava的操作符：retryWhen    
##### this.retryWhen { throwableObservable ->
#####        throwableObservable.flatMap { t ->
#####            if (t is IOException || t is HttpException) {
#####                //开始进行重试
#####                if (repeatCount < maxCount) {
 #####                   repeatCount += 1
 #####                   waitRetryTime = 1000 + repeatCount * 1000
#####                    Observable.just(1).delay(waitRetryTime.toLong(), TimeUnit.MILLISECONDS)
#####                } else {
#####                    Observable.error<T>(Throwable("重试次数已超过设置次数 = ${repeatCount}，即 不再重试"))
#####                }
#####            } else {
#####                Observable.error<T>(Throwable("发生了非网络异常（非I/O异常）"))
 #####           }
#####        }
#####    }
  
##### 2。单Activity+多fragment仿照知乎，优雅的处理生命周期问题
  
##### 3.视频播放采用当前流行的GSYVideoPlayer框架
##### 4.后期会加入检测glide加载图片的进度显示

# app展示：

![image](https://github.com/chenqi5256969/EyepetizerMVPKotlin/blob/master/image/Screenshot_2019-03-07-17-26-53-151_com.m163.eyepe.png)
![image](https://github.com/chenqi5256969/EyepetizerMVPKotlin/blob/master/image/Screenshot_2019-03-07-17-27-11-481_com.m163.eyepe.png)
![image](https://github.com/chenqi5256969/EyepetizerMVPKotlin/blob/master/image/Screenshot_2019-03-07-17-45-00-990_com.m163.eyepe.png)
![image](https://github.com/chenqi5256969/EyepetizerMVPKotlin/blob/master/image/Screenshot_2019-03-07-17-27-32-189_com.m163.eyepe.png)

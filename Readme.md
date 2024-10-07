﻿# Welcome to S-DES !

>信息安全导论作业1：S-DES算法实现；
本次作业由**欢乐斗地组**利用**Java+swing**完成了一个简单的关于S-DES加密、解密和暴力解密的可视化程序。
针对作业要求，成功完成了1-5关，实现了8bit二进制和任意bit的ASCII字符的加解密以及针对密钥的暴力破解的任务。


# 1. 实例展示

## 1.1 OverView
> 整个程序的整体框架如下所示，分别有：
- 加密界面
- 解密界面
- 暴力破解界面
  
![overview.gif](images%2Foverview.gif)

## 1.2 加密界面
> 在这里可以实现**8bit二进制明文**和**任意bit的ASCII字符**的加密，
但是同时只支持一种形式的加密，否则会报错；对于十位二进制的密钥，
可以选择**随机生成**或者**手动输入**，但是需要满足10位。具体展示如下动图：

![Encrypt.gif](images%2FEncrypt.gif)
## 1.3 解密界面
> 在这里可以实现**8bit二进制密文**和**任意bit的ASCII字符**的解密，
但是同时只支持一种形式的加密，否则会报错；对于十位二进制的密钥，
可以选择**随机生成**或者**手动输入**，但是需要满足10位。具体展示如下动图：

![Decrypt.gif](images%2FDecrypt.gif)

## 1.4 暴力破解
> 在这里可以支持用户提供**一对**或**多对**明密文对进行针对密钥的暴力解密，考虑到
**密钥碰撞**的原因，我们会遍历所有密钥空间，找出所有可能的密钥并进行验证。
具体展示如下动图:

![Force.gif](images%2FForce.gif)

# 2. 交叉验证
- **组内交叉验证** 针对加解密的结果（二进制和ASCII码）进行交叉验证，
发现可以完成逆向验证，这里只展示二进制的交叉验证如下图所示：

![confirm.gif](images%2Fconfirm.gif)

- **组间交叉验证** 我们同其他组（**荔枝组**（张芷芮，刘俐莹））针对加解密的结果（二进制和ASCII码）进行交叉验证，
  发现依然可以完成逆向验证。

# 3. 具体实现
- functionalClass文件夹 存放用于实现加解密和暴力破解的函数式算法，便于在ui界面中直接调用。
- UI文件夹 用于实现程序的ui界面和相关监听器的编写。
- images文件夹 存储了相关关卡测试的gif图像。
- 如果想要运行程序，可以直接点击src/UI/Main.java程序运行。

# 4. 封闭测试
> 根据第四关的结果（即暴力解密部分），进一步分析发现：
- 对于你随机选择的一个明密文对，是有不止一个密钥Key。
- 进一步扩展，对应明文空间任意给定的明文分组P_{n}，是否会出现选择不同的密钥K_{i}\ne K_{j}加密得到相同密文C_n的情况。
  >针对这一密钥碰撞问题，使用以下逻辑进行检测：
- 遍历所有可能的明文，程序遍历从 0 到 255 的所有可能的 8 位明文。
- 对每个明文尝试所有可能的密钥，对于每个明文，程序使用从 0 到 1023 的所有可能的 10 位密钥进行加密，记录加密结果：
- 对于每次加密，程序记录使用的密钥和产生的密文。
- 检查密钥碰撞，对于每个明文，程序检查是否有多个密钥产生了相同的密文。如果发现这种情况，就记录下来作为一个碰撞实例。
- 输出结果：程序最后输出所有发现的碰撞实例，包括明文、密文和产生相同密文的不同密钥。



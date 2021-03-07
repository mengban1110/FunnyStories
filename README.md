# FunnyStories

糗事百科service-后端管理系统&前台展示系统

# 更新日志

- 2021年3月7日03:47:42
  - 新增:注意点,请求方式 格式
  - 新增:4.4删除指定用户api
  - 修改:4.3修改用户指定数据api 用户状态新增 黑名单状态
  - 修复:修改错误的参数名描述和参数作用描述
- 2021年3月7日11:32:04
  - 修复:7.1获取黑名单成员信息 错误的json描述信息





------



# 0 : 注意事项

## 0.1 : 命名规范

Json中的key一律小写

## 0.2 : Json格式

```json
{
    "code": "200",       //状态码
    "msg": "请求成功",    //信息
    "data": {           //数据
        "username": "梦伴",
        "token": "awwerhsdvgsrhsdbhrwsdb51asffghjt"
    }
}
```

## 0.3 : 通用状态码描述

- 错误返回的负数，正确返回是正数
- 以下为通用情况下 返回的状态码 其他情况详情见api

| code | 解释               |
| ---- | ------------------ |
| 500  | 服务器内部错误     |
| 200  | 请求成功           |
| -1   | 未登录             |
| -2   | 非法调用(参数错误) |
| -404 | 请求方式错误       |

## 0.4 : 注意事项

- 本次开发的方向是论坛方向 可为以后增加相关项目经验
- 写完一个api 就自行postman测试 不要出现 本末倒置的情况
- 在后端管理系统中  管理员进行的 `增删改` 操作 都要向log表中写入!

- 因为是后台管理系统 所以只有登录 不开放注册

## 0.5 : 友情提示

> 如果不了解执行流程或者某些状态码
>
> 如果文档有误或者看不懂 
>
> 私聊问我 别自己琢磨导致耽误时间



> 本次是短期开发 3月20日要出第一版 所以大家珍惜时间 
>
> 难度不大 就是重复写sql  开头进度会比较慢 
>
> 但是后前中期的时候 代码格式基本cv 重复写sql罢了
>
> 
>
> 请每天按时学习上课内容,不要因为写项目 耽误课上内容,
>
> 所以我们每天都尽量少摸鱼, 只要顶过这次 也就解放了,也是对各位以后工作抗压能力的提升
>
> 以后再写东西就是框架了 快速开发 比现在会轻松很多
>
> 
>
> 每天我会询问大家学习情况,项目进度,做出适当调整



------



# 1 : 登录

注意点

1. 校验传参(注意校验顺序)
2. 需要在后台校验 用户名 密码
3. 登录成功后 需要刷新token

URL

```
/api/root/login
```

请求方式

```
Post
```

请求参数

| 参数名   | 示例             | 解释                   |
| -------- | ---------------- | ---------------------- |
| username | sadasasd         | 可能是 邮箱 或者是昵称 |
| password | yangsadas11121.. | 密码                   |

响应示例

```json
{
    "code": "200",       //响应代码
    "msg": "登录成功",    //提示信息
}
```

错误示例

```json
{
	"code":"-3",
	"msg":"请输入正确的账号"
}

{
	"code":"-4",
	"msg":"请输入正确的密码"
}
```



------



# 2 : 首页数据

## 2.1 : 文本数据

注意点

1. 校验传参(注意校验顺序)

URL

```
/api/home/getinfo
```

请求方式

```
Get
```

请求参数

| 参数名 | 示例                | 解释  |
| ------ | ------------------- | ----- |
| token  | dasd5112anio2anao21 | token |

响应示例

```json
{
	"msg":"请求成功",
	"code":200,
    "data":{
        "managername":"梦伴", "manageravatar":"http://file.qsub.cn/1b6ec965bf8a4bfda4b1039d000afb691608464547357.prifix",
        "serverruntime":"18时20分",//服务器已经运行时间
        "servermemory":"1231mb/2000mb",//服务器内存
        "servercores":12,//cpu核数
        "serverthreads":6,//cpu线程数
        "serveros":"windows 10",//服务器操作系统
        "serverjavaversion":"1.8.0_201",//服务器操作系统
        "countinfo":{
            "user":123,//用户数
            "posts":123,//共计发帖数
            "audits":123,//待审核帖子数
        },
        "rootlogs":[//时间排序 取最后五个 log表中的数据
            {
                "username":"梦伴",//用户名
                "ip":"192.168.0.1",//ip
                "content":"登录成功/注册成功/...",//操作信息
                "time":"2020-12-26 17:15:53",//操作时间
            },
            {
                "username":"梦伴",//用户名
                "ip":"192.168.0.1",//ip
                "content":"登录成功/注册成功/...",//操作信息
                "time":"2020-12-26 17:15:53",//操作时间
            },
            {
                "username":"梦伴",//用户名
                "ip":"192.168.0.1",//ip
                "content":"登录成功/注册成功/...",//操作信息
                "time":"2020-12-26 17:15:53",//操作时间
            },
            {
                "username":"梦伴",//用户名
                "ip":"192.168.0.1",//ip
                "content":"登录成功/注册成功/...",//操作信息
                "time":"2020-12-26 17:15:53",//操作时间
            },
            {
                "username":"梦伴",//用户名
                "ip":"192.168.0.1",//ip
                "content":"登录成功/注册成功/...",//操作信息
                "time":"2020-12-26 17:15:53",//操作时间
            }
        ],
        "userlogs":[//时间排序 取最后五个 log表中的数据
            {
                "username":"梦伴",//用户名
                "ip":"192.168.0.1",//ip
                "time":"2020-12-26 17:15:53",//操作时间
            },
            {
                "username":"梦伴",//用户名
                "ip":"192.168.0.1",//ip
                "time":"2020-12-26 17:15:53",//操作时间
            },
            {
                "username":"梦伴",//用户名
                "ip":"192.168.0.1",//ip
                "time":"2020-12-26 17:15:53",//操作时间
            },
            {
                "username":"梦伴",//用户名
                "ip":"192.168.0.1",//ip
                "time":"2020-12-26 17:15:53",//操作时间
            },
            {
                "username":"梦伴",//用户名
                "ip":"192.168.0.1",//ip
                "time":"2020-12-26 17:15:53",//操作时间
            }
        ],
        "hotwords":[//只要五个
            "123","123","123",...//热词  由搜索量从大到小排序
        ]
    }
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

## 2.2 : 近十天发帖数

注意点

1. 校验传参(注意校验顺序)

URL

```
/api/home/getpostnearcount

```

请求方式

```
Get

```

请求参数

| 参数名 | 示例                | 解释  |
| ------ | ------------------- | ----- |
| token  | dasd5112anio2anao21 | token |

响应示例

```json
{
	"msg":"请求成功",
	"code":200,
    "data":{//假设今天是2021年3月3日 近十天
		"dateaxis":['21日','22日','23日','24日','25日','26日','27日','28日','1日','2日'],  //横轴数据
		"postcount":[23,27,28,30,25,26,26,27,25,29],  //近十天数据
    }
}


```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

## 2.3 : 各个板块帖子数

注意点

1. 校验传参(注意校验顺序)

URL

```
/api/home/getpostpartcount

```

请求方式

```
Get

```

请求参数

| 参数名 | 示例                | 解释  |
| ------ | ------------------- | ----- |
| token  | dasd5112anio2anao21 | token |

响应示例

```json
{
	"msg":"请求成功",
	"code":200,
    "data":[
        {value: 10, name: '推荐板块'},
        {value: 73, name: '段子板块'},
        {value: 58, name: '视频板块'},
        {value: 48, name: '文字板块'}
    ]
}


```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```



------



# 3 : 网站后门

## 3.1 : 获取控制状态

注意点

1. 校验传参(注意校验顺序)

URL

```
/api/controller/getstatus

```

请求方式

```
Get

```

请求参数

| 参数名 | 示例                | 解释  |
| ------ | ------------------- | ----- |
| token  | dasd5112anio2anao21 | token |

响应示例

```json
{
	"msg":"请求成功",
	"code":200,
    "data":{
        "register":"0/1",//0 没有限制注册,1 限制注册中
        "login":"0/1",//0 没有限制登录,1 限制登录中
        "webopen":"0/1",//0 正常运营,1 闭站中
        "post":"0/1",//0 没有限制发帖,1 限制发帖
        "comment":"0/1"//0 没有关闭评论,1 关闭评论中
   }
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

## 3.1 : 写入注册控制状态

注意点

1. 校验传参(注意校验顺序)
2. 需要将操作信息写入后台日志中! 具体格式请看数据库.md中的后台日志表一列

URL

```
/api/controller/register/poststatus

```

请求方式

```
Post

```

请求参数

| 参数名 | 示例                | 解释                                 |
| ------ | ------------------- | ------------------------------------ |
| token  | dasd5112anio2anao21 | token                                |
| status | 1                   | 控制状态 0 没有限制注册,1 限制注册中 |

响应示例

```json
{
	"msg":"请求成功",
	"code":200,
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

## 3.2 : 写入登录控制状态

注意点

1. 校验传参(注意校验顺序)
2. 需要将操作信息写入后台日志中! 具体格式请看数据库.md中的后台日志表一列

URL

```
/api/controller/login/poststatus

```

请求方式

```
Post

```

请求参数

| 参数名 | 示例                | 解释                                 |
| ------ | ------------------- | ------------------------------------ |
| token  | dasd5112anio2anao21 | token                                |
| status | 1                   | 控制状态 0 没有限制登录,1 限制登录中 |

响应示例

```json
{
	"msg":"请求成功",
	"code":200,
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

## 3.3 : 写入网站运营控制状态

注意点

1. 校验传参(注意校验顺序)
2. 需要将操作信息写入后台日志中! 具体格式请看数据库.md中的后台日志表一列

URL

```
/api/controller/web/poststatus

```

请求方式

```
Post

```

请求参数

| 参数名 | 示例                | 解释                           |
| ------ | ------------------- | ------------------------------ |
| token  | dasd5112anio2anao21 | token                          |
| status | 1                   | 控制状态 0 正常运营,1 暂停运营 |

响应示例

```json
{
	"msg":"请求成功",
	"code":200,
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

## 3.4 : 写入发帖控制状态

注意点

1. 校验传参(注意校验顺序)
2. 需要将操作信息写入后台日志中! 具体格式请看数据库.md中的后台日志表一列

URL

```
/api/controller/post/poststatus

```

请求方式

```
Post

```

请求参数

| 参数名 | 示例                | 解释                               |
| ------ | ------------------- | ---------------------------------- |
| token  | dasd5112anio2anao21 | token                              |
| status | 1                   | 控制状态 0 没有限制发帖,1 限制发帖 |

响应示例

```json
{
	"msg":"请求成功",
	"code":200,
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

## 3.5 : 写入评论控制状态

注意点

1. 校验传参(注意校验顺序)
2. 需要将操作信息写入后台日志中! 具体格式请看数据库.md中的后台日志表一列

URL

```
/api/controller/comment/poststatus

```

请求方式

```
Post

```

请求参数

| 参数名 | 示例                | 解释                                   |
| ------ | ------------------- | -------------------------------------- |
| token  | dasd5112anio2anao21 | token                                  |
| status | 1                   | 控制状态 (0 没有关闭评论,1 关闭评论中) |

响应示例

```json
{
	"msg":"请求成功",
	"code":200,
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```



------



# 4 : 注册用户管理

## 4.1 : 获取用户信息

注意点

1. 校验传参(注意校验顺序)
2. 模糊搜索的是username

URL

```
/api/user/getinfo

```

请求方式

```
Get

```

请求参数

| 参数名 | 示例                | 解释                                |
| ------ | ------------------- | ----------------------------------- |
| token  | dasd5112anio2anao21 | token                               |
| word   | 旅人                | 关键词(如果本参数为空,则为查询所有) |
| page   | 1                   | 页数(如果为空 默认为1)              |
| size   | 20                  | 每页的数量(如果为空 默认为20)       |

响应示例

```json
{
    "code": 200,
    "msg": "获取成功",
    "data": {
        "users": [
            {
                "userid": 51, //用户id
                "username": "旅人在此", // 昵称
                "useravatar": "http://file.qsub.cn/1b7357.prifix", // 用户头像
                "usersex": "男", //性别
                "userbir": "2020-01-05", //生日
                "email": "2662252561@qq.com", //邮箱
                "usersign": "厄齐尔群翁群翁群无群无", //个签
            }, 
            {
                "userid": 51, //用户id
                "username": "旅人在此", // 昵称
                "useravatar": "http://file.qsub.cn/1b7357.prifix", // 用户头像
                "usersex": "男", //性别
                "userbir": "2020-01-05", //生日
                "email": "2662252561@qq.com", //邮箱
                "usersign": "厄齐尔群翁群翁群无群无", //个签
            }
        ]
    }
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

## 4.2 : 获取用户指定数据

注意点

1. 校验传参(注意校验顺序)

URL

```
/api/user/getdata

```

请求方式

```
Get

```

请求参数

| 参数名 | 示例                | 解释  |
| ------ | ------------------- | ----- |
| token  | dasd5112anio2anao21 | token |
| uid    | 21                  | uid   |

响应示例

```json
{
    "code": 200,
    "msg": "获取成功",
    "data": {
        "userid": 51, //用户id
        "username": "旅人在此", // 昵称
        "useravatar": "http://file.qsub.cn/1b7357.prifix", // 用户头像
        "usersex": "男", //性别
        "userbir": "2020-01-05", //生日
        "usersign": "厄齐尔群翁群翁群无群无", //个签
        "userstatus": 1 //userstatus: 0注册,未激活 1正常用户 2被禁止登录 3被禁止发帖 4被禁止评论
    }
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

{
	"code":"-3",
	"msg":"用户不存在"
}

```

## 4.3 : 修改用户指定数据

注意点

1. 本api请求体格式为 表单形式
2. 校验传参(注意校验顺序)
3. 见下表(请求参数) uid下面的所有参数 如果传输为空 则保持原数据 不进行更改
4. 如果传参userstatus = 2(被禁止登录) 就是被拉进黑名单了 需要在黑名单管理中添加对应数据
5. 需要将操作信息写入后台日志中! 具体格式请看数据库.md中的后台日志表一列

URL

```
/api/user/postdata

```

请求方式

```
Post

```

请求参数

| 参数名     | 示例                | 解释                                                         |
| ---------- | ------------------- | ------------------------------------------------------------ |
| token      | dasd5112anio2anao21 | token                                                        |
| uid        | 21                  | uid                                                          |
| username   | 梦伴                | 用户名                                                       |
| useravatar | D://Desktop//1.jpg  | 用户头像本机地址                                             |
| usersex    | 男                  | 用户性别                                                     |
| userbir    | 2000-11-10          | 用户生日                                                     |
| usersign   | 你好                | 用户个签                                                     |
| userstatus | 1                   | 用户状态(0注册未激活 1正常用户 2被禁止登录 3被禁止发帖 4被禁止评论 5黑名单) |
| password   | wushang816          | 用户密码                                                     |

响应示例

```json
{
    "code": 200,
    "msg": "修改成功",
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

## 4.4 : 删除指定用户

注意点

1. 校验传参(注意校验顺序)
2. 直接delete删除!!!
3. 相关操作信息需要写入后台日志中! 具体格式请看数据库.md中的后台日志表一列

URL

```
/api/user/deluser

```

请求方式

```
Post

```

请求参数

| 参数名 | 示例                | 解释  |
| ------ | ------------------- | ----- |
| token  | dasd5112anio2anao21 | token |
| uid    | 1                   | uid   |


响应示例

```json
{
    "code": 200,
    "msg": "删除成功",
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```



------



# 5 : 帖子管理

## 5.1 : 审核过了的帖子

### 5.1.1 : 获取审核过的所有帖子

注意点

1. 校验传参(注意校验顺序)
2. 获取的帖子信息不包括已推荐的帖子!

URL

```
/api/post/checked/getinfo

```

请求方式

```
Get

```

请求参数

| 参数名 | 示例                | 解释                          |
| ------ | ------------------- | ----------------------------- |
| token  | dasd5112anio2anao21 | token                         |
| page   | 1                   | 页数(如果为空 默认为1)        |
| size   | 20                  | 每页的数量(如果为空 默认为20) |


响应示例

```json
{
    "code": 200,
    "msg": "获取成功",
    "data": {
        "postinfo": [
            {
                "userinfo": {
                    "uname": "梦伴", //发帖人姓名
                    "useravatar": "http://file.qsub.cn/1b6ec965bf8a4bfda4b1039d000afb691608464547357.prifix" // 用户头像
                },
                "place": { //帖子模块的信息
                    "placeid": 1,
                    "placename": "图文"
                },
                "count":{
                    "like":25,//点赞数
                    "share":25,//分享数
                    "comment":25//评论数
                },
                "postid": 1, //帖子ID
                "posttext": "梦伴发送的第一条数字", // 文本数值
                "postimg": "http://file.qsub.cn/082cfad61e9a4461a93d786c9a248c111608481610394.jpg", //图片Url
                "postvideo": "http://file.qsub.cn/%E8%BF%85%E9%9B%B7%E5%BD%B1%E9%9F%B3%202020-12-05%2012-56-57.mp4", // 视频 如果有视频的话就没有图片了
                "createtime": "2020-12-26 17:15:53" //时间
            },
            {
                "userinfo": {
                    "uname": "梦伴", //发帖人姓名
                    "useravatar": "http://file.qsub.cn/1b6ec965bf8a4bfda4b1039d000afb691608464547357.prifix" // 用户头像
                },
                "placa": { //帖子模块的信息
                    "placaid": 1,
                    "placaname": "图文"
                },
                "count":{
                    "like":25,//点赞数
                    "share":25,//分享数
                    "comment":25//评论数
                },
                "postid": 1, //帖子ID
                "posttext": "梦伴发送的第一条数字", // 文本数值
                "postimg": "http://file.qsub.cn/082cfad61e9a4461a93d786c9a248c111608481610394.jpg", //图片Url
                "postvideo": "http://file.qsub.cn/%E8%BF%85%E9%9B%B7%E5%BD%B1%E9%9F%B3%202020-12-05%2012-56-57.mp4", // 视频 如果有视频的话就没有图片了
                "createtime": "2020-12-26 17:15:53" //时间
            }
        ]
    }
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

### 5.1.2 : 获取审核过的部分帖子

> json中的posttext postimg postvideo存在情况
>
> 段子区 : 只有posttext 没有postimg postvideo 字段
>
> 图文区 : 可能有posttext 必有postimg , 但是一定没有postvideo
>
> 视频区 : 可能有posttext 必有postvideo , 但是一定没有postimg 
>
> 
>
> 如果word传参为空就是空,而不是返回所有,比如word没有传参,则返回所有posttext为空的帖子

注意点

1. 校验传参(注意校验顺序)
2. 获取的帖子信息不包括已推荐的帖子!
3. 模糊搜索的是posttext

URL

```
/api/post/checked/getinfopart

```

请求方式

```
Get

```

请求参数

| 参数名 | 示例                | 解释                          |
| ------ | ------------------- | ----------------------------- |
| token  | dasd5112anio2anao21 | token                         |
| word   | 梦                  | 关键词(帖子文本信息模糊搜索)  |
| page   | 1                   | 页数(如果为空 默认为1)        |
| size   | 20                  | 每页的数量(如果为空 默认为20) |


响应示例

```json
{
    "code": 200,
    "msg": "获取成功",
    "data": {
        "postinfo": [
            {
                "userinfo": {
                    "uname": "梦伴", //发帖人姓名
                    "useravatar": "http://file.qsub.cn/1b6ec965bf8a4bfda4b1039d000afb691608464547357.prifix" // 用户头像
                },
                "place": { //帖子模块的信息
                    "placeid": 1,
                    "placename": "图文"
                },
                "count":{
                    "like":25,//点赞数
                    "share":25,//分享数
                    "comment":25//评论数
                },
                "postid": 1, //帖子ID
                "posttext": "梦伴发送的第一条数字", // 文本数值
                "postimg": "http://file.qsub.cn/082cfad61e9a4461a93d786c9a248c111608481610394.jpg", //图片Url
                "postvideo": "http://file.qsub.cn/%E8%BF%85%E9%9B%B7%E5%BD%B1%E9%9F%B3%202020-12-05%2012-56-57.mp4", // 视频 如果有视频的话就没有图片了
                "createtime": "2021年1月1日" //时间
            },
            {
                "userinfo": {
                    "uname": "梦伴", //发帖人姓名
                    "useravatar": "http://file.qsub.cn/1b6ec965bf8a4bfda4b1039d000afb691608464547357.prifix" // 用户头像
                },
                "placa": { //帖子模块的信息
                    "placaid": 1,
                    "placaname": "图文"
                },
                "count":{
                    "like":25,//点赞数
                    "share":25,//分享数
                    "comment":25//评论数
                },
                "postid": 1, //帖子ID
                "posttext": "梦伴发送的第一条数字", // 文本数值
                "postimg": "http://file.qsub.cn/082cfad61e9a4461a93d786c9a248c111608481610394.jpg", //图片Url
                "postvideo": "http://file.qsub.cn/%E8%BF%85%E9%9B%B7%E5%BD%B1%E9%9F%B3%202020-12-05%2012-56-57.mp4", // 视频 如果有视频的话就没有图片了
                "createtime": "2021年1月1日" //时间
            }
        ]
    }
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

### 5.1.3 : 推荐指定帖子

注意点

1. 校验传参(注意校验顺序)
2. 相关操作信息需要写入后台日志中! 具体格式请看数据库.md中的后台日志表一列

URL

```
/api/post/checked/commend

```

请求方式

```
Post

```

请求参数

| 参数名 | 示例                | 解释   |
| ------ | ------------------- | ------ |
| token  | dasd5112anio2anao21 | token  |
| postid | 1                   | 帖子id |


响应示例

```json
{
    "code": 200,
    "msg": "推荐成功",
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

### 5.1.4 : 删除指定帖子

注意点

1. 校验传参(注意校验顺序)
2. 删除帖子仅仅是修改数据库中display,0:不显示帖子 1:显示帖子
3. 相关操作信息需要写入后台日志中! 具体格式请看数据库.md中的后台日志表一列

URL

```
/api/post/checked/delpost

```

请求方式

```
Post

```

请求参数

| 参数名 | 示例                | 解释   |
| ------ | ------------------- | ------ |
| token  | dasd5112anio2anao21 | token  |
| postid | 1                   | 帖子id |


响应示例

```json
{
    "code": 200,
    "msg": "删除成功",
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

### 5.1.5 : 获取指定帖子的评论信息

注意点

1. 校验传参(注意校验顺序)
2. 模糊搜索的是评论文本

URL

```
/api/post/checked/getcomment

```

请求方式

```
Get

```

请求参数

| 参数名 | 示例                | 解释                                             |
| ------ | ------------------- | ------------------------------------------------ |
| token  | dasd5112anio2anao21 | token                                            |
| postid | 1                   | 帖子id                                           |
| word   | 你                  | 帖子评论内容(模糊搜索,如果本参数为空,则返回所有) |
| page   | 1                   | 页数(如果为空 默认为1)                           |
| size   | 20                  | 每页的数量(如果为空 默认为20)                    |


响应示例

```json
{
    "code": 200,
    "msg": "获取成功",
    "data": {
		"comments":[
            {
                "userinfo":{
                    "username": "旅人在此", // 评论人名字
                    "useravatar": "http://file.qsub.cn/1b7357.prifix", // 评论人头像
                },
                "commentid":1,//评论id
                "commenttext":"不错",//评论内容
                "likecount":231,//评论的被点赞数
                "createtime":"2020-12-26 17:15:53",//评论时间
            }
            {
                "userinfo":{
                    "username": "旅人在此", // 评论人名字
                    "useravatar": "http://file.qsub.cn/1b7357.prifix", // 评论人头像
                },
                "commentid":1,//评论id
                "commenttext":"不错",//评论内容
     			"likecount":231,//评论的被点赞数
                "createtime":"2020-12-26 17:15:53",//评论时间
            }
        ]
    }
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

### 5.1.6 : 删除指定帖子评论

注意点

1. 校验传参(注意校验顺序)
2. 删除评论仅仅是修改数据库中display,0:不显示评论 1:显示评论
3. 相关操作信息需要写入后台日志中! 具体格式请看数据库.md中的后台日志表一列

URL

```
/api/post/checked/delcomment

```

请求方式

```
Post

```

请求参数

| 参数名    | 示例                | 解释   |
| --------- | ------------------- | ------ |
| token     | dasd5112anio2anao21 | token  |
| commentid | 1                   | 评论id |


响应示例

```json
{
    "code": 200,
    "msg": "删除成功",
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```



## 5.2 : 待审核的帖子

### 5.2.1 : 获取待审核的所有帖子

注意点

1. 校验传参(注意校验顺序)

URL

```
/api/post/checking/getinfo

```

请求方式

```
Get

```

请求参数

| 参数名 | 示例                | 解释                          |
| ------ | ------------------- | ----------------------------- |
| token  | dasd5112anio2anao21 | token                         |
| page   | 1                   | 页数(如果为空 默认为1)        |
| size   | 20                  | 每页的数量(如果为空 默认为20) |


响应示例

```json
{
    "code": 200,
    "msg": "获取成功",
    "data": {
        "postinfo": [
            {
                "userinfo": {
                    "uname": "梦伴", //发帖人姓名
                    "useravatar": "http://file.qsub.cn/1b6ec965bf8a4bfda4b1039d000afb691608464547357.prifix" // 用户头像
                },
                "place": { //帖子模块的信息
                    "placeid": 1,
                    "placename": "图文"
                },
                "postid": 1, //帖子ID
                "posttext": "梦伴发送的第一条数字", // 文本数值
                "postimg": "http://file.qsub.cn/082cfad61e9a4461a93d786c9a248c111608481610394.jpg", //图片Url
                "postvideo": "http://file.qsub.cn/%E8%BF%85%E9%9B%B7%E5%BD%B1%E9%9F%B3%202020-12-05%2012-56-57.mp4", // 视频 如果有视频的话就没有图片了
                "createtime": "2020-12-26 17:15:53" //时间
            },
                        {
                "userinfo": {
                    "uname": "梦伴", //发帖人姓名
                    "useravatar": "http://file.qsub.cn/1b6ec965bf8a4bfda4b1039d000afb691608464547357.prifix" // 用户头像
                },
                "place": { //帖子模块的信息
                    "placeid": 1,
                    "placename": "图文"
                },
                "postid": 1, //帖子ID
                "posttext": "梦伴发送的第一条数字", // 文本数值
                "postimg": "http://file.qsub.cn/082cfad61e9a4461a93d786c9a248c111608481610394.jpg", //图片Url
                "postvideo": "http://file.qsub.cn/%E8%BF%85%E9%9B%B7%E5%BD%B1%E9%9F%B3%202020-12-05%2012-56-57.mp4", // 视频 如果有视频的话就没有图片了
                "createtime": "2020-12-26 17:15:53" //时间
            }
        ]
    }
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

### 5.2.2 : 获取待审核的部分帖子

> json中的posttext postimg postvideo存在情况
>
> 段子区 : 只有posttext 没有postimg postvideo 字段
>
> 图文区 : 可能有posttext 必有postimg , 但是一定没有postvideo
>
> 视频区 : 可能有posttext 必有postvideo , 但是一定没有postimg 
>
> 
>
> 如果word传参为空就是空,而不是返回所有,比如word没有传参,则返回所有posttext为空的帖子

注意点

1. 校验传参(注意校验顺序)

URL

```
/api/post/checking/getinfopart

```

请求方式

```
Get

```

请求参数

| 参数名 | 示例                | 解释                          |
| ------ | ------------------- | ----------------------------- |
| token  | dasd5112anio2anao21 | token                         |
| word   | 梦                  | 关键词(帖子文本信息模糊搜索)  |
| page   | 1                   | 页数(如果为空 默认为1)        |
| size   | 20                  | 每页的数量(如果为空 默认为20) |


响应示例

```json
{
    "code": 200,
    "msg": "获取成功",
    "data": {
        "postinfo": [
            {
                "userinfo": {
                    "uname": "梦伴", //发帖人姓名
                    "useravatar": "http://file.qsub.cn/1b6ec965bf8a4bfda4b1039d000afb691608464547357.prifix" // 用户头像
                },
                "place": { //帖子模块的信息
                    "placeid": 1,
                    "placename": "图文"
                },
                "postid": 1, //帖子ID
                "posttext": "梦伴发送的第一条数字", // 文本数值
                "postimg": "http://file.qsub.cn/082cfad61e9a4461a93d786c9a248c111608481610394.jpg", //图片Url
                "postvideo": "http://file.qsub.cn/%E8%BF%85%E9%9B%B7%E5%BD%B1%E9%9F%B3%202020-12-05%2012-56-57.mp4", // 视频 如果有视频的话就没有图片了
                "createtime": "2021年1月1日" //时间
            },
                        {
                "userinfo": {
                    "uname": "梦伴", //发帖人姓名
                    "useravatar": "http://file.qsub.cn/1b6ec965bf8a4bfda4b1039d000afb691608464547357.prifix" // 用户头像
                },
                "place": { //帖子模块的信息
                    "placeid": 1,
                    "placename": "图文"
                },
                "postid": 1, //帖子ID
                "posttext": "梦伴发送的第一条数字", // 文本数值
                "postimg": "http://file.qsub.cn/082cfad61e9a4461a93d786c9a248c111608481610394.jpg", //图片Url
                "postvideo": "http://file.qsub.cn/%E8%BF%85%E9%9B%B7%E5%BD%B1%E9%9F%B3%202020-12-05%2012-56-57.mp4", // 视频 如果有视频的话就没有图片了
                "createtime": "2021年1月1日" //时间
            }
        ]
    }
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

### 5.2.3 : 审核指定帖子

注意点

1. 校验传参(注意校验顺序)
2. 如果审核未通过则直接delete帖子 直接删除!!!!
3. 相关操作信息需要写入后台日志中! 具体格式请看数据库.md中的后台日志表一列

URL

```
/api/post/checking/auditpost

```

请求方式

```
Post

```

请求参数

| 参数名 | 示例                | 解释                             |
| ------ | ------------------- | -------------------------------- |
| token  | dasd5112anio2anao21 | token                            |
| postid | 1                   | 帖子id                           |
| audit  | 1                   | 审核状态(1/审核通过,0审核未通过) |


响应示例

```json
{
    "code": 200,
    "msg": "请求成功",
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```



## 5.3 : 推荐的帖子

### 5.3.1 : 获取推荐的所有帖子

注意点

1. 校验传参(注意校验顺序)

URL

```
/api/post/recommend/getinfo

```

请求方式

```
Get

```

请求参数

| 参数名 | 示例                | 解释                          |
| ------ | ------------------- | ----------------------------- |
| token  | dasd5112anio2anao21 | token                         |
| page   | 1                   | 页数(如果为空 默认为1)        |
| size   | 20                  | 每页的数量(如果为空 默认为20) |


响应示例

```json
{
    "code": 200,
    "msg": "获取成功",
    "data": {
        "postinfo": [
            {
                "userinfo": {
                    "uname": "梦伴", //发帖人姓名
                    "useravatar": "http://file.qsub.cn/1b6ec965bf8a4bfda4b1039d000afb691608464547357.prifix" // 用户头像
                },
                "place": { //帖子模块的信息
                    "placeid": 1,
                    "placename": "图文"
                },
                "count":{
                    "like":25,//点赞数
                    "share":25,//分享数
                    "comment":25//评论数
                },
                "postid": 1, //帖子ID
                "posttext": "梦伴发送的第一条数字", // 文本数值
                "postimg": "http://file.qsub.cn/082cfad61e9a4461a93d786c9a248c111608481610394.jpg", //图片Url
                "postvideo": "http://file.qsub.cn/%E8%BF%85%E9%9B%B7%E5%BD%B1%E9%9F%B3%202020-12-05%2012-56-57.mp4", // 视频 如果有视频的话就没有图片了
                "createtime": "2020-12-26 17:15:53" //时间
            },
            {
                "userinfo": {
                    "uname": "梦伴", //发帖人姓名
                    "useravatar": "http://file.qsub.cn/1b6ec965bf8a4bfda4b1039d000afb691608464547357.prifix" // 用户头像
                },
                "placa": { //帖子模块的信息
                    "placaid": 1,
                    "placaname": "图文"
                },
                "count":{
                    "like":25,//点赞数
                    "share":25,//分享数
                    "comment":25//评论数
                },
                "postid": 1, //帖子ID
                "posttext": "梦伴发送的第一条数字", // 文本数值
                "postimg": "http://file.qsub.cn/082cfad61e9a4461a93d786c9a248c111608481610394.jpg", //图片Url
                "postvideo": "http://file.qsub.cn/%E8%BF%85%E9%9B%B7%E5%BD%B1%E9%9F%B3%202020-12-05%2012-56-57.mp4", // 视频 如果有视频的话就没有图片了
                "createtime": "2020-12-26 17:15:53" //时间
            }
        ]
    }
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

### 5.3.2 : 获取推荐的部分帖子

> json中的posttext postimg postvideo存在情况
>
> 段子区 : 只有posttext 没有postimg postvideo 字段
>
> 图文区 : 可能有posttext 必有postimg , 但是一定没有postvideo
>
> 视频区 : 可能有posttext 必有postvideo , 但是一定没有postimg 
>
> 
>
> 如果word传参为空就是空,而不是返回所有,比如word没有传参,则返回所有posttext为空的帖子

注意点

1. 校验传参(注意校验顺序)
2. 模糊搜索的是posttext

URL

```
/api/post/recommend/getinfopart

```

请求方式

```
Get

```

请求参数

| 参数名 | 示例                | 解释                          |
| ------ | ------------------- | ----------------------------- |
| token  | dasd5112anio2anao21 | token                         |
| word   | 梦                  | 关键词(帖子文本信息模糊搜索)  |
| page   | 1                   | 页数(如果为空 默认为1)        |
| size   | 20                  | 每页的数量(如果为空 默认为20) |


响应示例

```json
{
    "code": 200,
    "msg": "获取成功",
    "data": {
        "postinfo": [
            {
                "userinfo": {
                    "uname": "梦伴", //发帖人姓名
                    "useravatar": "http://file.qsub.cn/1b6ec965bf8a4bfda4b1039d000afb691608464547357.prifix" // 用户头像
                },
                "place": { //帖子模块的信息
                    "placeid": 1,
                    "placename": "图文"
                },
                "count":{
                    "like":25,//点赞数
                    "share":25,//分享数
                    "comment":25//评论数
                },
                "postid": 1, //帖子ID
                "posttext": "梦伴发送的第一条数字", // 文本数值
                "postimg": "http://file.qsub.cn/082cfad61e9a4461a93d786c9a248c111608481610394.jpg", //图片Url
                "postvideo": "http://file.qsub.cn/%E8%BF%85%E9%9B%B7%E5%BD%B1%E9%9F%B3%202020-12-05%2012-56-57.mp4", // 视频 如果有视频的话就没有图片了
                "createtime": "2021年1月1日" //时间
            },
            {
                "userinfo": {
                    "uname": "梦伴", //发帖人姓名
                    "useravatar": "http://file.qsub.cn/1b6ec965bf8a4bfda4b1039d000afb691608464547357.prifix" // 用户头像
                },
                "placa": { //帖子模块的信息
                    "placaid": 1,
                    "placaname": "图文"
                },
                "count":{
                    "like":25,//点赞数
                    "share":25,//分享数
                    "comment":25//评论数
                },
                "postid": 1, //帖子ID
                "posttext": "梦伴发送的第一条数字", // 文本数值
                "postimg": "http://file.qsub.cn/082cfad61e9a4461a93d786c9a248c111608481610394.jpg", //图片Url
                "postvideo": "http://file.qsub.cn/%E8%BF%85%E9%9B%B7%E5%BD%B1%E9%9F%B3%202020-12-05%2012-56-57.mp4", // 视频 如果有视频的话就没有图片了
                "createtime": "2021年1月1日" //时间
            }
        ]
    }
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

### 5.3.3 : 取消推荐指定帖子

注意点

1. 校验传参(注意校验顺序)
2. 相关操作信息需要写入后台日志中! 具体格式请看数据库.md中的后台日志表一列

URL

```
/api/post/recommend/delrecommend

```

请求方式

```
Post

```

请求参数

| 参数名 | 示例                | 解释   |
| ------ | ------------------- | ------ |
| token  | dasd5112anio2anao21 | token  |
| postid | 1                   | 帖子id |


响应示例

```json
{
    "code": 200,
    "msg": "取消成功",
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```



------



# 6 : 广告管理

## 6.1 : 获取广告信息

注意点

1. 校验传参(注意校验顺序)

URL

```
/api/adv/getinfo

```

请求方式

```
Get

```

请求参数

| 参数名 | 示例                | 解释                                        |
| ------ | ------------------- | ------------------------------------------- |
| token  | dasd5112anio2anao21 | token                                       |
| word   | 你好                | 广告文本 模糊搜索,(如果本参数为空 返回所有) |
| page   | 1                   | 页数(如果为空 默认为1)                      |
| size   | 20                  | 每页的数量(如果为空 默认为20)               |


响应示例

```json
{
    "code": 200,
    "msg": "获取成功",
    "data": {
        "advert":[
            {
                "aid":123,//aid
                "acontext":"我是广告",
                "aimg":"http://file.dreamoforiginal.cn/adhahdniojsndinda.jpg",//图片地址
                "createtime":"2020-12-26 17:15:53"
            },
            {
                "aid":123,//aid
                "acontext":"我是广告",
                "aimg":"http://file.dreamoforiginal.cn/adhahdniojsndinda.jpg",//图片地址
                "createtime":"2020-12-26 17:15:53"
            }
        ]
    }
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

## 6.2 : 删除指定广告

注意点

1. 校验传参(注意校验顺序)
2. 相关操作信息需要写入后台日志中! 具体格式请看数据库.md中的后台日志表一列

URL

```
/api/adv/deladv

```

请求方式

```
Post

```

请求参数

| 参数名 | 示例                | 解释  |
| ------ | ------------------- | ----- |
| token  | dasd5112anio2anao21 | token |
| aid    | 2                   | aid   |


响应示例

```json
{
    "code": 200,
    "msg": "删除成功",
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

## 6.3 : 发布广告

注意点

1. 本api请求体格式为 表单形式
2. 校验传参(注意校验顺序)
3. 相关操作信息需要写入后台日志中! 具体格式请看数据库.md中的后台日志表一列

URL

```
/api/adv/postadv

```

请求方式

```
Post

```

请求参数

| 参数名   | 示例                | 解释                         |
| -------- | ------------------- | ---------------------------- |
| token    | dasd5112anio2anao21 | token                        |
| acontext | 我是广告            | 广告文本内容                 |
| aimg     | D://Desktop//1.jpg  | 广告图片地址(只能是一张图片) |


响应示例

```json
{
    "code": 200,
    "msg": "删除成功",
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```



------



# 7 : 黑名单管理

## 7.1 : 获取黑名单成员信息

注意点

1. 校验传参(注意校验顺序)
2. 模糊搜索的是username

URL

```
/api/blacklist/getinfo

```

请求方式

```
Get

```

请求参数

| 参数名 | 示例                | 解释                                        |
| ------ | ------------------- | ------------------------------------------- |
| token  | dasd5112anio2anao21 | token                                       |
| word   | 梦                  | username模糊搜索(如果本参数为空,则返回所有) |
| page   | 1                   | 页数(如果为空 默认为1)                      |
| size   | 20                  | 每页的数量(如果为空 默认为20)               |


响应示例

```json
{
    "code": 200,
    "msg": "获取成功",
    "data": {
        "blacklist":[
            {
                "bid":1,
                "userid":123,//userid
                "username":"梦伴",
                "useravatar":"http://file.dreamoforiginal.cn/adhahdniojsndinda.jpg",
                "createtime":"2020-12-26 17:15:53"//加入黑名单的时间
            },
            {
                "bid":1,
                "userid":123,//userid
                "username":"梦伴",
                "useravatar":"http://file.dreamoforiginal.cn/adhahdniojsndinda.jpg",
                "createtime":"2020-12-26 17:15:53"//加入黑名单的时间
            },
        ]
    }
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

## 7.2 : 取消黑名单

注意点

1. 校验传参(注意校验顺序)
2. 如果取消黑名单,则对应user表中的状态也应该恢复正常 恢复到1(正常用户)
3. 相关操作信息需要写入后台日志中! 具体格式请看数据库.md中的后台日志表一列

URL

```
/api/blacklist/delinfo

```

请求方式

```
Post

```

请求参数

| 参数名 | 示例                | 解释  |
| ------ | ------------------- | ----- |
| token  | dasd5112anio2anao21 | token |
| uid    | 1                   | uid   |


响应示例

```json
{
    "code": 200,
    "msg": "取消成功",
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```



------



# 8: 后台日志查询

## 8.1 : 获取后台日志信息

注意点

1. 校验传参(注意校验顺序)

URL

```
/api/history/getloginfo

```

请求方式

```
Get

```

请求参数

| 参数名 | 示例                | 解释                          |
| ------ | ------------------- | ----------------------------- |
| token  | dasd5112anio2anao21 | token                         |
| page   | 1                   | 页数(如果为空 默认为1)        |
| size   | 20                  | 每页的数量(如果为空 默认为20) |


响应示例

```json
{
    "code": 200,
    "msg": "获取成功",
    "data": {
        "logs":[
            {
                "rootid":213//帖子id
                "rootname":"梦伴",//管理员账号
                "rootavatar":"http://file.qsub.cn/1b7357.prifix",//管理员头像直链
                "content":"修改-uid:4-用户信息",
                "ip":"127.0.0.1"
            },
            {
                "rootid":213//帖子id
                "rootname":"梦伴",//管理员账号
                "rootavatar":"http://file.qsub.cn/1b7357.prifix",//管理员头像直链
                "content":"修改-uid:4-用户信息",
                "ip":"127.0.0.1"
            },
            {
                "rootid":213//帖子id
                "rootname":"梦伴",//管理员账号
                "rootavatar":"http://file.qsub.cn/1b7357.prifix",//管理员头像直链
                "content":"修改-uid:4-用户信息",
                "ip":"127.0.0.1"
            },
    }
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```



------



# 9 : 服务器控制

## 9.1 : 重启服务器

注意点

1. 校验传参(注意校验顺序)
2. 重启的是电脑!!!!!!!
3. 重启电脑之后自动启动tomcat
4. 相关操作信息需要写入后台日志中! 具体格式请看数据库.md中的后台日志表一列

URL

```
/api/os/restart

```

请求方式

```
Post

```

请求参数

| 参数名 | 示例                | 解释  |
| ------ | ------------------- | ----- |
| token  | dasd5112anio2anao21 | token |


响应示例

```json
{
    "code": 200,
    "msg": "请求成功",
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

## 9.2 : 重启Tomcat

注意点

1. 校验传参(注意校验顺序)
2. 重启的是Tomcat!!!!!!!
3. 需要先关闭tomcat 关闭之后再启动tomcat
4. 相关操作信息需要写入后台日志中! 具体格式请看数据库.md中的后台日志表一列

URL

```
/api/server/restart

```

请求方式

```
Post

```

请求参数

| 参数名 | 示例                | 解释  |
| ------ | ------------------- | ----- |
| token  | dasd5112anio2anao21 | token |


响应示例

```json
{
    "code": 200,
    "msg": "请求成功",
}

```

错误示例

```json
{
	"code":"-1",
	"msg":"未登录
}

{
	"code":"-2",
	"msg":"非法调用"
}

```

## 

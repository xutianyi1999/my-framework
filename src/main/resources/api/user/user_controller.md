    
**简要描述：** 

- 用户查询接口

**请求URL：** 
- ` http://localhost:8088/api/user/list `
  
**请求方式：**
- GET 

**参数：** 

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|pageNum  |是  |number |页码     |
|pageSize |是  |number |行数    |
|username |否  |string | 用户名    |
|email|否|string|邮箱|
|roleId|否|number|角色ID|

 **返回示例**

``` 
 {
     "status": "success",
     "data": [
         {
             "id": 11,
             "username": "喵喵喵ffdfdf",
             "email": "88833aaa93@qq.com",
             "status": 1,
             "createTime": "2019-11-11 16:16:40",
             "roleId": 1
         }
     ]
 }
```

 **返回参数说明** 

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|groupid |int   |用户组id，1：超级管理员；2：普通用户  |

 **备注** 

- 更多返回错误代码请看首页的错误代码描述



<%@ page contentType="text/html;charset=utf-8"%>
<%
request.setCharacterEncoding("utf-8");
%>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Synopsys</title>
  <link rel="icon" href="https://www.synopsys.com/etc/designs/synopsys/favicon.ico">

  <style>

    body {
      margin: 0;
      min-height: 520px;
    }

    .navbar {
      padding: 20px 12px;
      border-bottom: 1px solid #f0f0f0;
      background: #f0f0f4;
    }

    .logo {
      background-image: url('/static/assets/img/logo_coverity.png');
      background-position: 50% 50%;
      background-size: cover;
      background-repeat: no-repeat;
      height: 25px;
      width: 115px;
    }

    .logo-name {
      font-size: 13px;
      color: #80539c;
    }

    form {
      margin: auto;
      display:block;
      margin-top: 100px;
      width: 400px;

      padding: 24px 16px;

      border: 1px solid #f0f0f0;
      border-radius: 4px;
      box-shadow: 0px 6px 10px 0px rgba(0, 0, 0, 0.1);
    }

    .form-title {
      color: #000000;
      font-size: 24px;
      margin-bottom: 24px;
    }
    .input {
      border: 1px solid #f0f0f0;
      margin: 12px 0;
      display: -webkit-box;
      display: -webkit-flex;
      display: -ms-flexbox;
      display: flex;
      -webkit-box-align: center;
      -webkit-align-items: center;
      -ms-flex-align: center;
      align-items: center;
    }
    .input input {
      padding: 8px;
      border: none;
      background: transparent;
      line-height: 28px;
      -webkit-box-flex: 1;
      -webkit-flex: 1;
      -ms-flex: 1;
      flex: 1;
    }

    .input img {
      width: 24px;
      height: 24px;
      border-right: 1px solid #f0f0f0;
      padding: 4px 12px;
    }

    .action {
      text-align: right;
      padding: 12px;
    }

    .action button {
      border: none;
      color: #333333;
      padding: 6px 24px;
      border-radius: 4px;
      cursor: pointer;
    }

    .action button:hover {
      background: #80539c;
      color: #ffffff;
    }

    footer {
      text-align: center;
      width: 100%;
      position: fixed;
      bottom: 0;

      height: 48px;
      color: #999999;
    }

    @media (max-height: 520px) {
      footer {
        position: relative;
        margin-top: 40px;
      }
    }

  </style>
</head>
<body>
<header class="cx-nav">
  <div class="navbar">
    <div class="nav-brand">
      <a href="">
        <div class="logo"></div>
      </a>
      <div class="logo-name">Coverity V2020.09</div>
    </div>
  </div>
</header>

<content>
  <form action="/login" method="post" >
    <div class="form-title">登录</div>
    <div class="input icon-left">
      <img src="/static/assets/img/user.png">
      <input name="username" type="text" placeholder="用户名">
    </div>
    <div class="input icon-left">
      <img src="/static/assets/img/passwod.png">
      <input name="password" type="password" placeholder="密码">
    </div>
	<div class="input icon-left">
      <img src="/static/assets/img/host.png">
      <input name="host" type="text" placeholder="Coverity URL">
    </div>
    <div class="action">
      <button type="submit">登录</button>
    </div>

  </form>
</content>

<footer>
  ©2021 Synopsys, Inc. All Rights Reserved
</footer>
</body>

</html>
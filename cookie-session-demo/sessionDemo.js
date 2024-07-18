import express from 'express';
import session from 'express-session';
import cookieParser from 'cookie-parser';
import bodyParser from 'body-parser';

const app = express();
const PORT = 3000;

// 使用 cookie-parser 中间件
app.use(cookieParser());

// 使用 body-parser 中间件
app.use(bodyParser.urlencoded({ extended: true }));

// 配置 Session 中间件
app.use(session({
    name: 'sessionId',
    secret: 'your_secret_key',
    resave: false,
    saveUninitialized: false,
    cookie: {
        maxAge: 24 * 60 * 60 * 1000, // Cookie 有效期为 1 天
        secure: false, // 仅在 HTTPS 连接上发送 Cookie
        httpOnly: true, // 禁止客户端 JavaScript 访问 Cookie
        sameSite: 'lax' // 控制 Cookie 是否随跨站请求发送
    }
}));

// 模拟用户数据库
const users = {
    'user1': 'user1',
    'user2': 'password2'
};

// 登录页面路由
app.get('/login', (req, res) => {
    res.send(`
        <form method="post" action="/login">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
            <br>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            <br>
            <button type="submit">Login</button>
        </form>
    `);
});

// 处理登录请求的路由
app.post('/login', (req, res) => {
    const { username, password } = req.body;
    if (users[username] && users[username] === password) {
        req.session.user = username;
        res.redirect('/dashboard');
    } else {
        res.send('Invalid username or password');
    }
});

// 受保护的路由
app.get('/dashboard', (req, res) => {
    if (req.session.user) {
        res.send(`
            <h1>Welcome, ${req.session.user}!</h1>
            <a href="/logout">Logout</a>
        `);
    } else {
        res.redirect('/login');
    }
});

// 注销路由
app.get('/logout', (req, res) => {
    req.session.destroy(err => {
        if (err) {
            return res.send('Error logging out');
        }
        res.clearCookie('sessionId');
        res.redirect('/login');
    });
});

// 启动服务器
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});

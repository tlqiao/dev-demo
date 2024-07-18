import express from 'express';
import cookieParser from 'cookie-parser';

const app = express();
const PORT = 3001;
// 使用 cookie-parser 中间件
app.use(cookieParser());
// 主页路由
app.get('/', (req, res) => {
    // 获取现有的用户行为数据
    let userBehavior = req.cookies.userBehavior;
    if (!userBehavior) {
        userBehavior = { visits: 0, lastVisit: new Date().toISOString() };
    } else {
        userBehavior = JSON.parse(userBehavior);
    }
    // 更新用户行为数据
    userBehavior.visits += 1;
    userBehavior.lastVisit = new Date().toISOString();
    // 设置新的 Cookie，过期时间为 30 天
    res.cookie('userBehavior', JSON.stringify(userBehavior), { maxAge: 30 * 24 * 60 * 60 * 1000, httpOnly: true });
    // 响应用户行为数据
    res.send(`
        <h1>欢迎访问本站！</h1>
        <p>您已访问本站 ${userBehavior.visits} 次</p>
        <p>上次访问时间：${userBehavior.lastVisit}</p>
    `);
});

// 启动服务器
app.listen(PORT, () => {
    console.log(`服务器已启动，访问 http://localhost:${PORT}`);
});

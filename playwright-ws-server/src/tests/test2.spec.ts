import { chromium } from 'playwright';

(async () => {
        // Launch browser
        const browser = await chromium.launch({ headless: false });
        const context = await browser.newContext();
        const page = await context.newPage();
        await browser.close();
})();

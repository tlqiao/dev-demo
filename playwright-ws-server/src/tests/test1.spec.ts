import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('https://www.thoughtworks.com/');
  await page.getByLabel('Insights').click();
  await page.getByLabel('Technology', { exact: true }).click();
  await page.getByLabel('Investors').click();
  await page.getByRole('button', { name: 'Earnings' }).click();
});
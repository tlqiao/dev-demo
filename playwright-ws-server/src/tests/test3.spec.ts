import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  // Recording...
}); await page.goto('https://angularjs.realworld.io/#/register');
await expect(page.getByPlaceholder('Username')).toBeVisible();
await expect(page.getByPlaceholder('Email')).toBeVisible();
await expect(page.getByPlaceholder('Password')).toBeVisible();
await expect(page.getByRole('link', { name: 'Home' })).toBeVisible();
await expect(page.getByRole('link', { name: 'Sign in' })).toBeVisible();
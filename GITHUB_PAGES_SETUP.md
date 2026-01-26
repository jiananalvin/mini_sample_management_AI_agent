# GitHub Pages Setup Instructions

If you're seeing the README instead of your Angular app, follow these steps:

## Fix GitHub Pages Source

1. Go to your GitHub repository: `https://github.com/jiananalvin/mini_sample_management_AI_agent`
2. Click **Settings** (top menu)
3. Scroll down to **Pages** (left sidebar)
4. Under **Source**, you should see:
   - **Deploy from a branch** ❌ (This is wrong - it serves from the branch)
   - **GitHub Actions** ✅ (This is correct - it serves from the workflow)

5. **If it says "Deploy from a branch":**
   - Click the dropdown and select **"GitHub Actions"**
   - Click **Save**

6. **If it already says "GitHub Actions":**
   - The workflow should deploy automatically
   - Check the **Actions** tab to see if the workflow ran successfully
   - If it failed, check the error messages

## Verify Deployment

1. Go to **Actions** tab in your repository
2. Look for the "Deploy Frontend to GitHub Pages" workflow
3. Click on the latest run
4. Check if all steps completed successfully (green checkmarks)
5. If there are errors, click on the failed step to see details

## After Fixing

Once you change the source to "GitHub Actions":
- The workflow will automatically deploy on the next push
- Or you can manually trigger it: **Actions** → **Deploy Frontend to GitHub Pages** → **Run workflow**

Your app should be available at:
`https://jiananalvin.github.io/mini_sample_management_AI_agent/`

## Troubleshooting

**Still seeing README?**
- Wait a few minutes for GitHub Pages to update
- Clear your browser cache (Ctrl+Shift+Delete)
- Try incognito/private browsing mode
- Check if the workflow completed successfully in the Actions tab

**Workflow failing?**
- Check the error message in the Actions tab
- Make sure Node.js version is 22 (already fixed)
- Verify the build output path is correct

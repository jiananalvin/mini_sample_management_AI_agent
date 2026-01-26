# Quick Start Guide - Deploy to GitHub

Follow these steps to push your project to GitHub and deploy it:

## Step 1: Initialize Git (if not done)

Run the PowerShell script:
```powershell
.\setup-git.ps1
```

Or manually:
```powershell
git init
git branch -M main
git add .
git commit -m "Initial commit: Mini Sample Management AI Agent"
```

## Step 2: Create GitHub Repository

1. Go to https://github.com/new
2. Repository name: `mini_sample_management_AI_agent`
3. **DO NOT** check "Initialize with README"
4. Click "Create repository"

## Step 3: Push to GitHub

```powershell
git remote add origin https://github.com/YOUR_USERNAME/mini_sample_management_AI_agent.git
git push -u origin main
```

Replace `YOUR_USERNAME` with your actual GitHub username.

## Step 4: Enable GitHub Pages

1. Go to your repository → **Settings** → **Pages**
2. Under **Source**, select:
   - **Deploy from a branch**
   - Branch: `main`
   - Folder: `/ (root)`
3. Click **Save**

The GitHub Actions workflow will automatically deploy your frontend when you push changes.

## Step 5: Deploy Backend

Choose one of these free options:

### Railway (Easiest)
1. Go to https://railway.app
2. Sign up with GitHub
3. Click **New Project** → **Deploy from GitHub repo**
4. Select your repository
5. Set root directory to `backend`
6. Railway will auto-deploy
7. Copy the URL (e.g., `https://your-app.railway.app`)

### Render
1. Go to https://render.com
2. Sign up with GitHub
3. Click **New** → **Web Service**
4. Connect your repository
5. Set:
   - Root Directory: `backend`
   - Build Command: `./mvnw clean package -DskipTests`
   - Start Command: `java -jar target/backend-0.0.1-SNAPSHOT.jar`
6. Click **Create Web Service**

## Step 6: Configure GitHub Secret

1. Go to your repository → **Settings** → **Secrets and variables** → **Actions**
2. Click **New repository secret**
3. Name: `API_URL`
4. Value: Your backend URL + `/api` (e.g., `https://your-app.railway.app/api`)
5. Click **Add secret**

## Step 7: Trigger Deployment

Push any change to trigger deployment:

```powershell
git add .
git commit -m "Configure deployment"
git push
```

## Step 8: Access Your App

After deployment (check GitHub Actions tab):

- **Frontend**: `https://YOUR_USERNAME.github.io/mini_sample_management_AI_agent/`
- **Backend**: Your Railway/Render URL

## Troubleshooting

- **Frontend not loading?** Check GitHub Actions workflow status
- **CORS errors?** Make sure backend URL is set in GitHub secrets
- **Backend not working?** Check Railway/Render logs

For detailed instructions, see [DEPLOYMENT.md](DEPLOYMENT.md)

# Deployment Guide

This guide will help you deploy your Mini Sample Management AI Agent application to GitHub and make it accessible via a public link.

## Prerequisites

1. A GitHub account
2. Git installed on your local machine
3. (Optional) Railway or Render account for backend deployment

## Step 1: Initialize Git Repository

If you haven't already initialized git:

```bash
git init
git add .
git commit -m "Initial commit"
```

## Step 2: Create GitHub Repository

1. Go to [GitHub](https://github.com) and create a new repository
2. Name it: `mini_sample_management_AI_agent`
3. **DO NOT** initialize with README, .gitignore, or license (we already have these)
4. Copy the repository URL

## Step 3: Push to GitHub

```bash
git remote add origin https://github.com/YOUR_USERNAME/mini_sample_management_AI_agent.git
git branch -M main
git push -u origin main
```

Replace `YOUR_USERNAME` with your GitHub username.

## Step 4: Enable GitHub Pages

1. Go to your repository on GitHub
2. Click **Settings** → **Pages**
3. Under **Source**, select:
   - **Deploy from a branch**
   - Branch: `main` or `gh-pages`
   - Folder: `/ (root)` or `/docs`
4. Click **Save**

**Note:** The GitHub Actions workflow will automatically deploy to GitHub Pages when you push changes.

## Step 5: Deploy Backend

You have several options for deploying the Spring Boot backend:

### Option A: Railway (Recommended - Free Tier Available)

1. Go to [Railway](https://railway.app)
2. Sign up/login with GitHub
3. Click **New Project** → **Deploy from GitHub repo**
4. Select your repository
5. Railway will auto-detect it's a Java project
6. Set the root directory to `backend`
7. Add environment variable:
   - `CORS_ALLOWED_ORIGINS`: `https://YOUR_USERNAME.github.io`
8. Railway will provide a URL like: `https://your-app.railway.app`
9. Update the GitHub Actions workflow secret `API_URL` with this URL

### Option B: Render (Free Tier Available)

1. Go to [Render](https://render.com)
2. Sign up/login with GitHub
3. Click **New** → **Web Service**
4. Connect your GitHub repository
5. Configure:
   - **Name**: `mini-sample-backend`
   - **Root Directory**: `backend`
   - **Build Command**: `./mvnw clean package -DskipTests`
   - **Start Command**: `java -jar target/backend-0.0.1-SNAPSHOT.jar`
   - **Environment**: `Java`
6. Add environment variable:
   - `CORS_ALLOWED_ORIGINS`: `https://YOUR_USERNAME.github.io`
7. Click **Create Web Service**
8. Copy the provided URL and update GitHub Actions secret `API_URL`

### Option C: Heroku (Requires Credit Card)

1. Install Heroku CLI
2. Login: `heroku login`
3. Create app: `heroku create mini-sample-backend`
4. Set buildpack: `heroku buildpacks:set heroku/java`
5. Deploy: `git subtree push --prefix backend heroku main`
6. Set config: `heroku config:set CORS_ALLOWED_ORIGINS=https://YOUR_USERNAME.github.io`

## Step 6: Configure GitHub Secrets

1. Go to your repository → **Settings** → **Secrets and variables** → **Actions**
2. Click **New repository secret**
3. Add:
   - **Name**: `API_URL`
   - **Value**: Your backend URL (e.g., `https://your-app.railway.app/api`)

## Step 7: Update Frontend Environment

After deploying the backend, update the GitHub Actions workflow file `.github/workflows/deploy-frontend.yml`:

1. Replace `https://your-backend-url.railway.app/api` with your actual backend URL
2. Or ensure the `API_URL` secret is set correctly

## Step 8: Trigger Deployment

Push any change to trigger the deployment:

```bash
git add .
git commit -m "Configure deployment"
git push
```

## Step 9: Access Your Application

After deployment completes:

- **Frontend**: `https://YOUR_USERNAME.github.io/mini_sample_management_AI_agent/`
- **Backend**: Your Railway/Render URL (e.g., `https://your-app.railway.app`)

## Troubleshooting

### Frontend not loading
- Check GitHub Actions workflow status
- Verify `baseHref` in `angular.json` matches your repository name
- Check browser console for errors

### CORS errors
- Ensure backend CORS configuration includes your GitHub Pages URL
- Check backend logs for CORS-related errors
- Verify `API_URL` secret is set correctly

### Backend not responding
- Check Railway/Render logs
- Verify environment variables are set
- Ensure port is configured correctly (Railway/Render auto-assigns PORT)

## Updating the Application

Simply push changes to the `main` branch:

```bash
git add .
git commit -m "Your update message"
git push
```

GitHub Actions will automatically rebuild and redeploy both frontend and backend.

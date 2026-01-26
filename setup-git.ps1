# PowerShell script for GitHub deployment setup
# Run this script to initialize git and prepare for GitHub push

Write-Host "🚀 Setting up Mini Sample Management AI Agent for GitHub deployment..." -ForegroundColor Cyan

# Initialize git if not already initialized
if (-not (Test-Path ".git")) {
    Write-Host "📦 Initializing git repository..." -ForegroundColor Yellow
    git init
    git branch -M main
}

# Add all files
Write-Host "📝 Adding files to git..." -ForegroundColor Yellow
git add .

# Check if there are changes to commit
$status = git status --porcelain
if ($status) {
    Write-Host "💾 Committing changes..." -ForegroundColor Yellow
    git commit -m "Initial commit: Mini Sample Management AI Agent"
} else {
    Write-Host "✅ No changes to commit" -ForegroundColor Green
}

Write-Host ""
Write-Host "✅ Setup complete!" -ForegroundColor Green
Write-Host ""
Write-Host "Next steps:" -ForegroundColor Cyan
Write-Host "1. Create a repository on GitHub named: mini_sample_management_AI_agent"
Write-Host "2. Run these commands:"
Write-Host "   git remote add origin https://github.com/YOUR_USERNAME/mini_sample_management_AI_agent.git"
Write-Host "   git push -u origin main"
Write-Host ""
Write-Host "3. Enable GitHub Pages in repository settings"
Write-Host "4. Deploy backend to Railway/Render (see DEPLOYMENT.md)"
Write-Host "5. Set API_URL secret in GitHub repository settings"

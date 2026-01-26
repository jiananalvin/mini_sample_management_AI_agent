#!/bin/bash

# Setup script for GitHub deployment
# Run this script to initialize git and prepare for GitHub push

echo "🚀 Setting up Mini Sample Management AI Agent for GitHub deployment..."

# Initialize git if not already initialized
if [ ! -d ".git" ]; then
    echo "📦 Initializing git repository..."
    git init
    git branch -M main
fi

# Add all files
echo "📝 Adding files to git..."
git add .

# Check if there are changes to commit
if git diff --staged --quiet; then
    echo "✅ No changes to commit"
else
    echo "💾 Committing changes..."
    git commit -m "Initial commit: Mini Sample Management AI Agent"
fi

echo ""
echo "✅ Setup complete!"
echo ""
echo "Next steps:"
echo "1. Create a repository on GitHub named: mini_sample_management_AI_agent"
echo "2. Run these commands:"
echo "   git remote add origin https://github.com/YOUR_USERNAME/mini_sample_management_AI_agent.git"
echo "   git push -u origin main"
echo ""
echo "3. Enable GitHub Pages in repository settings"
echo "4. Deploy backend to Railway/Render (see DEPLOYMENT.md)"
echo "5. Set API_URL secret in GitHub repository settings"

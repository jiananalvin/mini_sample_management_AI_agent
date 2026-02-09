# OpenAI API Key Setup Guide

## ⚠️ Security Warning

**NEVER commit your OpenAI API key to Git or share it publicly!**

## For Local Development

### Option 1: Environment Variable (Recommended)

**Windows (PowerShell):**
```powershell
$env:OPENAI_API_KEY="your-api-key-here"
```

**Windows (Command Prompt):**
```cmd
set OPENAI_API_KEY=your-api-key-here
```

**Linux/Mac:**
```bash
export OPENAI_API_KEY="your-api-key-here"
```

To make it permanent, add to your shell profile (`~/.bashrc`, `~/.zshrc`, etc.):
```bash
export OPENAI_API_KEY="your-api-key-here"
```

### Option 2: application.properties (NOT for Git)

Only use this for local testing, and **NEVER commit it**:

```properties
openai.api.key=your-api-key-here
```

**Important**: Make sure `application.properties` is NOT committed with your key!

## For Deployment (Railway/Render)

### Railway

1. Go to your Railway project dashboard
2. Click on your service
3. Go to **Variables** tab
4. Click **New Variable**
5. Add:
   - **Name**: `OPENAI_API_KEY`
   - **Value**: `your-api-key-here`
6. Click **Add**

### Render

1. Go to your Render dashboard
2. Select your service
3. Go to **Environment** section
4. Click **Add Environment Variable**
5. Add:
   - **Key**: `OPENAI_API_KEY`
   - **Value**: `your-api-key-here`
6. Click **Save Changes**

### Heroku

```bash
heroku config:set OPENAI_API_KEY=your-api-key-here
```

## Verify It's Working

After setting the environment variable:

1. Restart your backend application
2. Try creating a sample using natural language
3. Check backend logs if there are any errors

## Getting Your API Key

1. Go to [OpenAI Platform](https://platform.openai.com/api-keys)
2. Sign up or log in
3. Click **Create new secret key**
4. Copy the key immediately (you won't see it again!)
5. Store it securely (password manager recommended)

## Troubleshooting

### "OpenAI API key is not configured"
- Verify the environment variable name is exactly `OPENAI_API_KEY`
- Restart your backend after setting the variable
- Check for typos in the variable name

### API errors
- Verify your API key is correct
- Check you have credits in your OpenAI account
- Check backend logs for detailed error messages

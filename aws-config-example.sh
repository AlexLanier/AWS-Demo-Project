#!/bin/bash

# AWS Configuration Example Script
# This script shows how to set up AWS environment variables
# DO NOT put actual credentials in this file - use it as a template

echo "AWS Configuration Example"
echo "========================="
echo ""
echo "To configure AWS credentials, you have several options:"
echo ""

echo "Option 1: Set environment variables (replace with your actual values):"
echo "export AWS_ACCESS_KEY_ID=your_access_key_here"
echo "export AWS_SECRET_ACCESS_KEY=your_secret_key_here"
echo "export AWS_REGION=us-east-1"
echo ""

echo "Option 2: Use AWS CLI (recommended):"
echo "aws configure"
echo ""

echo "Option 3: Create credentials file manually:"
echo "mkdir -p ~/.aws"
echo "cat > ~/.aws/credentials << EOF"
echo "[default]"
echo "aws_access_key_id = your_access_key_here"
echo "aws_secret_access_key = your_secret_key_here"
echo "EOF"
echo ""
echo "cat > ~/.aws/config << EOF"
echo "[default]"
echo "region = us-east-1"
echo "EOF"
echo ""

echo "After setting up credentials, you can run the application:"
echo "mvn exec:java"
echo ""

echo "To test if your credentials are working:"
echo "aws sts get-caller-identity"
echo ""

echo "Security Note: Never commit actual AWS credentials to version control!"

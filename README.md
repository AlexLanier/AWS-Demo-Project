# AWS Demo Project

A Java Maven project demonstrating AWS SDK v2 usage for various AWS services including EC2, S3, Lambda, SQS, and Step Functions.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- AWS Account (optional - for actual AWS operations)

## Project Structure

```
src/main/java/aws/demo/
├── AwsDemoApp.java          # Main application entry point
├── ec2/
│   └── EC2Checker.java      # EC2 instance listing
├── s3/
│   └── S3Uploader.java      # S3 file upload functionality
├── lambda/
│   └── LambdaInvoker.java   # Lambda function invocation
├── sqs/
│   └── SQSPublisher.java    # SQS message publishing
└── step/
    └── StepFunctionRunner.java # Step Functions execution
```

## Setup and Configuration

### 1. AWS Credentials Setup

To use AWS services, you need to configure your AWS credentials. Choose one of the following methods:

#### Option A: AWS CLI Configuration (Recommended)
```bash
# Install AWS CLI if you haven't already
# macOS: brew install awscli
# Linux: pip install awscli

# Configure AWS credentials
aws configure
```

You'll be prompted to enter:
- AWS Access Key ID
- AWS Secret Access Key
- Default region (e.g., us-east-1)
- Default output format (json)

#### Option B: Environment Variables
```bash
export AWS_ACCESS_KEY_ID=your_access_key
export AWS_SECRET_ACCESS_KEY=your_secret_key
export AWS_REGION=us-east-1
```

#### Option C: AWS Credentials File
Create `~/.aws/credentials`:
```ini
[default]
aws_access_key_id = your_access_key
aws_secret_access_key = your_secret_key
```

Create `~/.aws/config`:
```ini
[default]
region = us-east-1
```

### 2. Getting AWS Credentials

1. **Create an AWS Account** (if you don't have one)
2. **Create an IAM User**:
   - Go to AWS Console → IAM → Users → Create User
   - Attach policies for the services you want to use:
     - `AmazonEC2ReadOnlyAccess` (for EC2)
     - `AmazonS3FullAccess` (for S3)
     - `AWSLambda_FullAccess` (for Lambda)
     - `AmazonSQSFullAccess` (for SQS)
     - `AWSStepFunctionsFullAccess` (for Step Functions)
3. **Generate Access Keys**:
   - Select your user → Security credentials → Create access key
   - Save the Access Key ID and Secret Access Key

## Building and Running

### Compile the Project
```bash
mvn compile
```

### Run the Application
```bash
mvn exec:java
```

### Package the Application
```bash
mvn package
```

## Usage

The application currently runs in demo mode with the following features:

1. **EC2 Instance Listing**: Lists all EC2 instances in your account
2. **S3 Upload**: Ready for file uploads (commented out to prevent accidental uploads)
3. **Lambda Invocation**: Ready for function invocation (commented out to prevent accidental invocations)
4. **SQS Message Publishing**: Ready for message sending (commented out to prevent accidental messages)
5. **Step Functions Execution**: Ready for workflow execution (commented out to prevent accidental executions)

### Enabling Actual AWS Operations

To enable actual AWS operations, uncomment the relevant lines in `AwsDemoApp.java`:

```java
// Uncomment these lines to enable actual operations:
// S3Uploader.uploadFile("your-bucket", "your-key", "your-file.txt");
// LambdaInvoker.invoke("your-function-name", "{\"test\": \"data\"}");
// SQSPublisher.sendMessage("your-queue-url", "your message");
// StepFunctionRunner.startExecution("your-state-machine-arn", "{\"test\": \"data\"}");
```

## Error Handling

The application includes comprehensive error handling:
- AWS service exceptions are caught and logged
- Resource cleanup is handled automatically using try-with-resources
- Meaningful error messages are displayed

## Dependencies

- AWS SDK v2 for Java (BOM version 2.25.19)
- Maven Exec Plugin for easy execution

## Troubleshooting

### Common Issues

1. **Region not specified**: Set `AWS_REGION` environment variable or configure it in AWS CLI
2. **Credentials not found**: Ensure AWS credentials are properly configured
3. **Permission denied**: Check IAM policies for the required permissions
4. **SLF4J warnings**: These are harmless logging warnings and don't affect functionality

### Getting Help

- Check AWS SDK documentation: https://docs.aws.amazon.com/sdk-for-java/
- AWS CLI documentation: https://docs.aws.amazon.com/cli/
- IAM documentation: https://docs.aws.amazon.com/iam/

## Security Notes

- Never commit AWS credentials to version control
- Use IAM roles with minimal required permissions
- Consider using AWS STS for temporary credentials
- Regularly rotate your access keys

## License

This project is for educational purposes. Feel free to modify and use as needed.
trigger test

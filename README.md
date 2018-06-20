# AWS Lambda for backup indices:

  A parameter of type BackupData should be provided to the Lambda for the Backup information.

```json  
{
 "host": "host of the elastic search to be backedup",
 "bucket": "s3 bucket were the backup will be stores",
 "role": "role that will execute the backup"

}
```

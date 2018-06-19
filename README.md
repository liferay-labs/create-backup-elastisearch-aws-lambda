# AWS Lambda for delete old indices:

  A parameter of type String should be provided tothe Lambda for the AWS ES host.
All the indices older that 10 days and match the pattern: (filebeat-* or metricbeat-*) will be deleted.# create-backup-elastisearch-aws-lambda

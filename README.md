# eks-application-examples
create some simple resources with AWS SDK and applications.

# below is an example pod manifest with IRSA credential.
apiVersion: v1
kind: Pod
metadata:
  name: s3-access-pod
spec:
  serviceAccountName: irsa-test-sa
  containers:
    - name: s3-access-container
      image: [your image repository]/[name]
      command: ["sleep"]
      args: ["infinity"]
      env:
        - name: AWS_STS_REGIONAL_ENDPOINTS
          value: regional
        - name: AWS_DEFAULT_REGION
          value: ap-yourregion-1
        - name: AWS_REGION
          value: ap-yourregion-1
        - name: AWS_ROLE_ARN
          value: arn:aws:iam::[your account id]:role/[the role for IRSA]
        - name: AWS_WEB_IDENTITY_TOKEN_FILE
          value: /var/run/secrets/eks.amazonaws.com/serviceaccount/token
      volumeMounts:
        - mountPath: /var/run/secrets/eks.amazonaws.com/serviceaccount
          name: aws-iam-token
          readOnly: true
  volumes:
    - name: aws-iam-token
      projected:
        sources:
          - serviceAccountToken:
              audience: sts.amazonaws.com
              expirationSeconds: 86400
              path: token
  automountServiceAccountToken: true

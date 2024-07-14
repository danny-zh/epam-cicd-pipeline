def call(Map config = [:])
{
    echo "Testing from branch ${config.branch}"
    sh 'npm test'
}
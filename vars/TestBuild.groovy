def test_build(Map config = [:])
{
    echo "Testing from branch ${config.branch}"
    sh 'npm test'
}
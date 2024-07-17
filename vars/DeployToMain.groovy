def call(Map config = [:])
{
    echo "Deploying from branch ${config.BRANCH}"
    def downstreamJobName = "deploy_to_$config.BRANCH"
    def downstreamBuild = build job: downstreamJobName, wait: true, parameters: [
                        string(name: 'IMAGE_TAG', value: "$config.IMAGE_TAG"),
                        string(name: 'NAME', value: "$config.CONT_NAME"),
                        string(name: 'PORT', value: "$config.PORT"),
                        string(name: 'APP_PORT', value: "$config.APP_PORT")
                    ]
                    
    echo "Downstream build result: ${downstreamBuild.result}"
}
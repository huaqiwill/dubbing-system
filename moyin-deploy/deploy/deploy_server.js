/*
只部署服务端
*/
import { compileMavenProject } from '../common/common.build'
import { syncServer } from '../common/common.deploy'

async function deployUser() {
    await compileMavenProject()
    await syncServer()
}

deployUser()

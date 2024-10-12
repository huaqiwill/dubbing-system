/*
只部署前端
*/
import { compileUserProject } from '../common/common.build'
import { syncUser } from '../common/common.deploy'

async function deployUser() {
    await compileUserProject()
    await syncUser()
}

deployUser()

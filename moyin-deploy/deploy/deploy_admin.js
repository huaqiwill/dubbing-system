import { compileAdminProject } from '../common/common.build'
import { syncAdmin } from '../common/common.deploy'

async function deployAdmin() {
    await compileAdminProject()
    await syncAdmin()
}

deployAdmin()

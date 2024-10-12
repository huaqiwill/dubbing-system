/**
 * 部署时数据库相关操作
 */

import { dumpMysqlScrit } from '../common/common.build'
import { syncDatabase } from '../common/common.deploy'

async function deployMySQL() {
    await dumpMysqlScrit()
    await syncDatabase()
}

deployMySQL()

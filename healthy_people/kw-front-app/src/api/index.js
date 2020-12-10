import user from './user'
import login from './login'
import transfer from './transfer'
import transaction from './transaction'
import home from './home'

const api = {
  ...home,
  ...user,
  ...login,
  ...transfer,
  ...transaction
}

export default api

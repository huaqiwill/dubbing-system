import Cookies from 'js-cookie'

const tokenKey = 'Admin-Token'

/**
 *
 * @returns
 */
export function getToken() {
  return Cookies.get(tokenKey)
}

/**
 *
 * @param token
 * @returns
 */
export function setToken(token: string) {
  return Cookies.set(tokenKey, token)
}

/**
 * 
 * @returns
 */
export function removeToken() {
  return Cookies.remove(tokenKey)
}

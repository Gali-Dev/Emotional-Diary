// 로그인 상태관리
const LOGIN = 'LOGIN';
export const login = () => ({ type: LOGIN });

const LOGOUT = 'LOGOUT';
export const logout = () => ({ type: LOGOUT });

const initstate = {
  lsLogin: false,
};

const reducer = (state = initstate, action) => {
  console.log('store에서 찍힘 ' + action);
  switch (action.type) {
    case LOGIN:
      return { isLogin: true };
    case LOGOUT:
      return { isLogin: false };
    default:
      return state;
  }
};
export default reducer;

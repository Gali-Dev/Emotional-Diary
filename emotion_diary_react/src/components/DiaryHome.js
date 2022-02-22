import { useSelector } from 'react-redux';
import styled from 'styled-components';
import Header from './Header';
import Main from './Main';

const DiaryHomeStyle = styled.div`
  display: grid;
  height: 100%;
  grid-template-columns: 20% 70%;
  align-items: center;
  justify-content: center;
  grid-column-gap: 3%;
  justify-items: center;
`;
const DiaryHome = () => {
  const isLogin = useSelector((store) => store.isLogin);
  console.log('DiaryHome에서 로그인 확인', isLogin);

  return (
    <DiaryHomeStyle>
      <Header />
      <Main />
    </DiaryHomeStyle>
  );
};

export default DiaryHome;

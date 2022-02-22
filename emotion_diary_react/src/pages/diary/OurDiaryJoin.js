import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const OurDiaryJoinStyle = styled.div`
  width: 100%;
  max-width: 850px;
  height: 100%;
`;

const TitleStyle = styled.div`
  font-size: 50px;
  text-align: center;
  margin: 20px 20px;
`;
const LabelStyle = styled.div`
  font-size: 23px;
`;
const ButtonBoxStyle = styled.div`
  display: grid;
  grid-template-columns: auto auto;
  grid-column-gap: 10px;
  margin: 40px 0px 0px 0px;
  justify-content: end;
`;
const ButtonStyle = styled.button`
  width: 80px;
`;

const OurDiaryJoin = () => {
  //tcode tname
  ///together/save
  const [group, setGroup] = useState({
    tname: '',
    tcode: '',
  });
  function inputHandle(e) {
    setGroup((prevState) => {
      // 함수형으로 쓰는 이유 : setstate 두번쓸때 값을 들고오기 우ㅐㅎ서
      return {
        ...prevState,
        [e.target.name]: e.target.value,
      };
    });
  }
  function reset(e) {
    e.preventDefault();
    setGroup({
      tname: '',
      tcode: '',
    });
  }
  function submitJoin(e) {
    e.preventDefault();
    console.log('그룹생성', JSON.stringify(group));
    fetch('http://10.100.102.31:8000/together/save', {
      method: 'POST',
      headers: {
        Authorization: localStorage.getItem('Authorization'),
        'Content-Type': 'application/json; charset=utf-8',
      },
      body: JSON.stringify(group),
    })
      .then((res) => res.text())
      .then((res) => {
        console.log(res);
        if (res === 'ok') {
          alert('그룹을 생성 했습니다.');
          window.location.replace('/diary');
        } else {
          console.log(res);
          alert('그룹 생성을 실패했습니다.');
        }
      });
  }
  return (
    <OurDiaryJoinStyle>
      <form>
        <TitleStyle>그룹만들기</TitleStyle>
        <div className="form-group">
          <LabelStyle>그룹 명</LabelStyle>
          <input
            className="form-control form-control-lg"
            type="text"
            name="tname"
            value={group.tname}
            onChange={inputHandle}
            placeholder="그룹 명을 입력하세요"
          />
          <div className="form-group"></div>
          <LabelStyle>코드</LabelStyle>
          <input
            className="form-control form-control-lg"
            type="text"
            name="tcode"
            value={group.tcode}
            onChange={inputHandle}
            placeholder="인증할 코드를 입력하세요"
          />
        </div>
        <ButtonBoxStyle>
          <Link to="/login">
            <ButtonStyle
              type="button"
              className="btn btn-secondary btn-sm"
              onClick={reset}
            >
              취소
            </ButtonStyle>
          </Link>
          <ButtonStyle
            type="button"
            className="btn btn-secondary btn-sm"
            onClick={submitJoin}
          >
            그룹생성
          </ButtonStyle>
        </ButtonBoxStyle>
      </form>
    </OurDiaryJoinStyle>
  );
};

export default OurDiaryJoin;

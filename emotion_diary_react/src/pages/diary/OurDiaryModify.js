import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const OurDiaryModifyStyle = styled.div`
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

const OurDiaryModify = (props) => {
  const tno = props.match.params.id;
  const [group, setGroup] = useState({
    tno: tno,
    tname: '',
    tcode: '',
  });
  console.log('tno', tno);

  useEffect(() => {
    fetch('http://10.100.102.31:8000/together/get/' + tno, {
      method: 'GET',
      headers: {
        Authorization: localStorage.getItem('Authorization'),
      },
    })
      .then((res) => res.json())
      .then((res) => {
        setGroup(res);
        console.log('res', res);
      });
  }, []);
  function inputHandle(e) {
    console.log(group);
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
  function submitModify(e) {
    e.preventDefault();
    fetch('http://10.100.102.31:8000/together/update/' + tno, {
      method: 'PUT',
      headers: {
        Authorization: localStorage.getItem('Authorization'),
        'Content-Type': 'application/json; charset=utf-8',
      },
      body: JSON.stringify(group),
    })
      .then((res) => res.text())
      .then((res) => {
        console.log(res);
        if (res === 'together update') {
          alert('수정 했습니다.');
          window.location.replace('/diary');
        } else {
          console.log(res);
          alert('수정을 실패했습니다.');
        }
      });
  }
  return (
    <OurDiaryModifyStyle>
      <form>
        <TitleStyle>그룹 수정</TitleStyle>
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
            onClick={submitModify}
          >
            수정
          </ButtonStyle>
        </ButtonBoxStyle>
      </form>
    </OurDiaryModifyStyle>
  );
};

export default OurDiaryModify;

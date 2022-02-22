import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import styled from 'styled-components';

const DetailStyle = styled.div`
  display: grid;
  grid-template-columns: auto;
  width: 100%;
  max-width: 850px;
  height: 98%;
  margin-top: 40px;
  padding: 25px 20px 10px 20px;
  background-color: white;
  //border: 1px solid #003458;
`;
const DateStyle = styled.div`
  display: grid;
  grid-template-columns: 70% 30%;
`;

const ImageStyle = styled.img`
  width: 70%;
`;
const TitleStyle = styled.div`
  font-size: 32px;
`;
const ContentStyle = styled.div`
  font-size: 28px;
`;
const ButtonBoxStyle = styled.div`
  display: grid;
  grid-template-columns: auto auto auto;
  grid-column-gap: 10px;
  justify-content: end;
  height: fit-content;
  margin: 10px;
`;
const ButtonStyle = styled.button`
  width: 80px;
`;
const LabelStyle = styled.div`
  font-size: 23px;
`;
const DiaryDetail = (props) => {
  const [diary, setDiary] = useState([]);
  const id = props.match.params.id;
  const history = useHistory();
  const [same, setSame] = useState(false);
  const userNo = localStorage.getItem('userNo');

  useEffect(() => {
    fetch('http://10.100.102.31:8000/board/get/' + id, {
      method: 'GET',
      headers: {
        Authorization: localStorage.getItem('Authorization'),
      },
    })
      .then((res) => res.json())
      .then((res) => {
        console.log(res.member.mno);
        setDiary(res);
        if (res.member.mno == userNo) {
          setSame(true);
        }
      });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  function submitDelete(e) {
    e.preventDefault();

    console.log('submitDelete() 실행');
    console.log(id);

    if (window.confirm('삭제하시겠습니까?')) {
      fetch('http://10.100.102.31:8000/board/' + id, {
        method: 'DELETE',
        headers: {
          Authorization: localStorage.getItem('Authorization'),
        },
      })
        .then((res) => res.text())
        .then((res) => {
          if (res === 'ok') {
            alert('삭제 되었습니다.');
            history.push('/diary/');
          } else {
            alert('삭세 실패했습니다.');
          }
        });
    } else {
    }
  }
  function test() {
    console.log('detail', diary.member.mno);
    console.log('memberid', localStorage.getItem('userNo'));
  }
  return (
    <DetailStyle className="jumbotron">
      <DateStyle>
        <h3 class="display-4">{diary.createTime}</h3>
        <ImageStyle src={'/images/' + diary.emotion + '.png'} alt="" />
      </DateStyle>
      <LabelStyle>제목</LabelStyle>
      <TitleStyle>{diary.title}</TitleStyle>
      <LabelStyle>내용</LabelStyle>
      <ContentStyle>{diary.contents}</ContentStyle>
      <ButtonBoxStyle>
        <ButtonStyle
          type="button"
          className="btn btn-secondary btn-sm"
          onClick={() => {
            history.push('/diary');
          }}
        >
          돌아가기
        </ButtonStyle>
        {/* 보드 id 멤버 id 비교 */}
        {same ? (
          <>
            <ButtonStyle
              type="button"
              className="btn btn-secondary btn-sm"
              onClick={() => {
                history.push('/diary/modify/' + id);
              }}
            >
              수정
            </ButtonStyle>
            <ButtonStyle
              type="button"
              className="btn btn-secondary btn-sm"
              // onClick={submitDelete}
              onClick={submitDelete}
            >
              삭제
            </ButtonStyle>
          </>
        ) : null}
      </ButtonBoxStyle>
    </DetailStyle>
  );
};
export default DiaryDetail;

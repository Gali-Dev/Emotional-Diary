import React, { useEffect, useState } from 'react';
import styled from 'styled-components';

const AnalysisStyle = styled.div`
  //display: grid;
  grid-template-columns: auto;
  width: 100%;
  max-width: 850px;
  height: 100%;
  padding: 40px 20px 10px 20px;
  text-align: center;
`;
const TitleStyle = styled.div`
  font-size: 50px;
  text-align: center;
  margin: 20px 20px;
`;
const ButtonStyle = styled.button`
  background-color: transparent;
  font-family: 'TDTDTadakTadak';
  font-size: ${(props) => (props.user ? '15px' : '20px')};
  // border: transparent;
  outline: transparent;
  text-align: left;
`;
const Header1Style = styled.div`
  text-align: center;
`;
const Header2Style = styled.div``;
const GroupBoxStyle = styled.div`
  text-align: center;
`;
const KindStyle = styled.div`
  display: grid;
  grid-template-columns: auto auto auto auto auto;
`;
const ImgStyle = styled.img`
  display: grid;
  width: 100%;
`;
const Analysis = () => {
  const [groups, setGroups] = useState([]);
  const [images, setImages] = useState();
  const [image, setImage] = useState('');
  const [showImage, setShowImage] = useState(false);

  //그룹목록
  useEffect(() => {
    fetch('http://10.100.102.31:8000/tmember/get', {
      method: 'GET',
      headers: {
        Authorization: localStorage.getItem('Authorization'),
      },
    })
      .then((res) => res.json())
      .then((res) => {
        setGroups(res);
      });
  }, []);

  function myDiary() {
    setShowImage(false);
    const user = localStorage.getItem('userNo');
    fetch('http://10.100.102.31:8000/board/analysis/my', {
      method: 'GET',
      headers: {
        Authorization: localStorage.getItem('Authorization'),
      },
    })
      .then((res) => res.text())
      .then((res) => {
        if (res === 'ok') {
          console.log('mydiary');
          setImages({
            wordcloud:
              'http://10.100.102.90:7000/static/my/wordcloud' + user + '.png',
            line: 'http://10.100.102.90:7000/static/my/line' + user + '.png',
            bar: 'http://10.100.102.90:7000/static/my/bar' + user + '.png',
            pie: 'http://10.100.102.90:7000/static/my/pie' + user + '.png',
            raider:
              'http://10.100.102.90:7000/static/my/raider' + user + '.png',
          });
          setShowImage(true);
        }
      });
  }
  function groupDiary(props) {
    setShowImage(false);
    fetch('http://10.100.102.31:8000/board/analysis/group/' + props, {
      method: 'GET',
      headers: {
        Authorization: localStorage.getItem('Authorization'),
      },
    })
      .then((res) => res.text())
      .then((res) => {
        if (res === 'ok') {
          setImages({
            wordcloud:
              'http://10.100.102.90:7000/static/together/wordcloud' +
              props +
              '.png',
            line:
              'http://10.100.102.90:7000/static/together/line' + props + '.png',
            bar:
              'http://10.100.102.90:7000/static/together/bar' + props + '.png',
            pie:
              'http://10.100.102.90:7000/static/together/pie' + props + '.png',
            raider:
              'http://10.100.102.90:7000/static/together/raider' +
              props +
              '.png',
          });
          setShowImage(true);
        }
      });
  }

  function selectGraph(props) {
    if (props === 1) {
      setImage(images.wordcloud);
    } else if (props === 2) {
      setImage(images.line);
    } else if (props === 3) {
      setImage(images.bar);
    } else if (props === 4) {
      setImage(images.pie);
    } else if (props === 5) {
      setImage(images.raider);
    }
  }
  return (
    <AnalysisStyle>
      <TitleStyle>내 글 분석</TitleStyle>
      <Header1Style className="btn-group" role="group">
        <ButtonStyle
          type="button"
          className="btn btn-secondary disabled btn-sm"
          onClick={() => myDiary()}
        >
          나의 모든 일기
        </ButtonStyle>
        <ButtonStyle
          type="button"
          className="btn btn-secondary disabled btn-sm"
          onClick={() => groupDiary('0')}
        >
          나의 일기
        </ButtonStyle>
      </Header1Style>
      <br />
      <br />
      <Header2Style>
        <GroupBoxStyle className="btn-group" role="group">
          {groups.map(({ tmno, member, together }) => (
            <ButtonStyle
              type="button"
              className="btn btn-secondary disabled btn-sm"
              key={tmno}
              onClick={() => groupDiary(together.tno)}
            >
              {together.tname}
            </ButtonStyle>
          ))}
        </GroupBoxStyle>
      </Header2Style>
      <br />
      <div>눌러주세요</div>

      <div>
        {showImage ? (
          <>
            <KindStyle className="btn-group" role="group">
              <button
                type="button"
                className="btn btn-secondary btn-sm"
                onClick={() => selectGraph(1)}
              >
                wordcloud
              </button>
              <button
                type="button"
                className="btn btn-secondary btn-sm"
                onClick={() => selectGraph(2)}
              >
                line
              </button>
              <button
                type="button"
                className="btn btn-secondary btn-sm"
                onClick={() => selectGraph(3)}
              >
                bar
              </button>
              <button
                type="button"
                className="btn btn-secondary btn-sm"
                onClick={() => selectGraph(4)}
              >
                pie
              </button>
              <button
                type="button"
                className="btn btn-secondary btn-sm"
                onClick={() => selectGraph(5)}
              >
                raider
              </button>
            </KindStyle>
            <div>
              <ImgStyle src={image} alt="" />
            </div>
          </>
        ) : (
          <div>분석 중 입니다.</div>
        )}
      </div>
    </AnalysisStyle>
  );
};

export default Analysis;

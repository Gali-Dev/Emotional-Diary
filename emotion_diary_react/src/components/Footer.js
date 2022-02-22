import React from 'react';
import styled from 'styled-components';

const FooterStyle = styled.div`
  display: grid;
  grid-template-columns: auto;
  justify-content: center;
  font-size: 21px;
`;
const Footer = () => {
  return (
    <FooterStyle>
      Copyright © 2018 tcpschool.co.,Ltd. All rights reserved.
      <br />
      Contact webmaster for more information. 070-1234-5678
    </FooterStyle>
  );
};

export default Footer;

import styled from 'styled-components'
import { Link } from "react-router-dom";

export const NavBox = styled.nav`
    margin-top: 40px;
    margin-bottom: 40px;
`
export const EnlaceBox = styled.div`
    background: #3257DB;
    padding: 10px 0;
    margin-top: ${props => props.first ? 0 : "20px"};
    font-size: 20px;
    border: 1px solid #3257DB;
    color: #fff;
    font-weight: bold;
    div{
        display: flex;
        justify-content: ${props => props.first ? "none" : "space-around"};
        align-items: center;
        gap: 20px;
        cursor: pointer;
    }
`
export const LinkList = styled(Link)`
    div{
        display: flex;
        justify-content: space-around;
        align-items: center;
    }
    h4{
        padding-top: 20px;
        padding-left: 40px;
        display: flex;
        align-items: center;
        gap: 10px;
        @media screen and (max-width: 800px){
            padding-left: 50px;
        }
    }
  text-decoration: none;
  display: block;
  color: #fff;
  font-weight: bold;
  &:hover{
      color: #67e0ff;
  }
  &.link__actual{
    color: #67e0ff;
  }
`
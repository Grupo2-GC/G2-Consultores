import styled from "styled-components";


export const ErrorFormLogin = styled.div`
  border: 0;
  border-radius: 10px;
  background: rgb(218, 71, 71);
  margin-top: 10px;
  h3{
    margin: 10px;
    font-size: 15px;
    padding: 10px;
    text-align: center;
    color: #fff;
  }
  @media (max-width: 500px) {
      h3{
        margin: 0;
        padding: 10px 5px;
        font-size: 15px; 
      }
    }
`
export const Formulario = styled.form`
  padding-left: 70px;
  padding-top: 50px;
  display: grid;
  /* grid-template-rows: 1fr; */
  grid-template-columns: repeat(2,1fr);
  background: #fff;
  border-radius: 5px;

    @media screen and (max-width: 1540px) {
        grid-template-columns: 1fr;
    }
    @media screen and (max-width: 500px) {
        padding-left: 10px;
    }
`;
export const Box1 = styled.div`
  padding-right: 90px;
  padding-bottom: 30px;
`;
export const TitleForm = styled.h2`
  text-align: start;
  font-size: 22px;
  font-weight: 700;
  line-height: 26px;
  color: #4d4f5c;
  @media screen and (max-width: 500px) {
        font-size: 18px;
    }
`;
export const Hr = styled.hr`
    border: 1px solid #e6e6e6;
`
export const GridForm = styled.div`
    display: grid;
  /* grid-template-columns: repeat(3, 1fr); */
    grid-template-columns: repeat(2, 1fr);
    gap: 20px;

    @media screen and (max-width: 1540px) {
        grid-template-columns: repeat(3, 1fr);
    }
    @media screen and (max-width: 500px) {
        grid-template-columns: repeat(1, 1fr);
    }
`;
export const GrupoForm = styled.div`
`;
export const LabelForm = styled.label`
    display: block;
    font-weight: medium;
    padding: 10px;
    font-size: 20px;
    cursor: pointer;
    color: #172b4d;
    @media (max-height: 850px) {
        font-size: 15px;
    }
`
export const InputForm = styled.input`
    width: 100%;
	background: #fff;
	border: 3px solid transparent;
	border-radius: 5px;
	height: 50px;
	line-height: 45px;
	padding: 0 20px;
	transition: .3s ease all;
    border: 3px solid #a3a6b4;
    text-align:left;
    font-size: 20px;
    color: #4d4f5c;
    @media screen and (max-width: 500px) {
        width: 250px;
    }
    &:focus{
        border: 3px solid #0075FF;
        outline: none;
        box-shadow: 3px 0px 30px rgba(163,163,163, 0.4);
    }
    /* &[type=date]{
        text-transform: uppercase;
    } */
    &[type=number]::-webkit-inner-spin-button,
    &[type=number]::-webkit-outer-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }
    &[type=number] {
        -moz-appearance: textfield;
    }
    &[type=submit] {
        text-align: center;
    }
`
export const SelectForm = styled.select`
    background: #fff;
    text-align: left;
    padding-left: 20px;
    width: 100%;
    height: 50px;
    font-size: 16px;
    font-weight: bold;
    border-radius: 5px;
    border: none;
    border: 3px solid #a3a6b4;
    color: #7d818a;
    font-weight: medium;
    @media screen and (max-width: 500px) {
        width: 250px;
    }
    
    &:focus{
        border: 3px solid #0075FF;
        outline: none;
        box-shadow: 3px 0px 30px rgba(163,163,163, 0.4);
    }
`
export const BotonForm = styled.div`
    grid-column-start: 1;
    /* grid-column-end: 4; */
    grid-column-end: 3;
    input{
        background: #000;
        color: #fff;
        font-weight: bold;
        border-radius: 3px;
        border: none;
        cursor: pointer;
        transition: background-color .3s ease all;
        &:hover{
            background: #3257db;
        }
    }
    @media screen and (max-width: 1540px) {
        grid-column-start: 1;
        grid-column-end: 4; 
    }
    @media screen and (max-width: 500px) {
        grid-column-start: 1;
        grid-column-end: 2; 
    }
`

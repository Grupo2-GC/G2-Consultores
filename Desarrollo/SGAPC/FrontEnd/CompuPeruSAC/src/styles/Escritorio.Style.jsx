import styled from 'styled-components';

export const ContainerBox = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 30px;
    gap: 50px;
    @media screen and (max-width: 1000px) {
        flex-direction: column-reverse;
        gap: 15px;
    }
`
export const ImageEscritorio = styled.div`
    img{
        width: 500px;
        @media screen and (max-width: 1000px) {
            width: 300px;
        }
    }
`
export const CardContainer = styled.div`
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(293px, 1fr));
`

export const CardSala = styled.div`
    width: 293px;
    height: 338px;
    background: #fff;
    border-radius: 10px;
    box-shadow: 0px 0px 25px 5px rgba(0,0,0,0.1);
    margin-bottom: 30px;
`
export const CardEstado = styled.div`
    padding: 25px;
    display: flex;
    justify-content: space-between;
    img{
        width: 80px;
        height: 80px;
    }
`
export const CardInfo1 = styled.div`
    h4{
        font-size: 20px;
        font-weight: 600;
        color: #4D4F5C;
    }
    p{
        font-size: 20px;
        font-weight: bold;
        color: #57D2A9;
    }
`
export const CardInfoSub = styled.div`
    padding: 5px 25px;
    display: flex;
    justify-content: space-between;
    h4{
        font-size: 16px;
        font-weight: 600;
        color: #131523;
    }
    h3{
        font-size: 16px;
        font-weight: 600;
        color: #4D4F5C;
    }
    p{
        font-size: 16px;
        font-weight: 500;
        color: #4D4F5C;
    }
`
export const Hr = styled.hr`
    color: #D7DBEC;
    margin: 10px 0; 
`
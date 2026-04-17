-- V10: layers 테이블에 pixel_data 컬럼 추가
-- 파일 스토리지 서비스 구현 전까지 canvas base64 데이터를 직접 저장

ALTER TABLE layers ADD COLUMN pixel_data TEXT;

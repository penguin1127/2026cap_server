-- V11: 시드 계정 비밀번호 해시 수정
-- V8에 삽입된 해시가 'password123'과 불일치하여 로그인 불가 문제 수정
-- 올바른 BCrypt 해시 (password123, cost=10)

UPDATE users
SET password_hash = '$2a$10$EGCuGdIj3ApVYMwOIMeVm.L7VY8JaPW0DV52r3kldNWVXojjIA.dG'
WHERE email IN (
    'spriteknight@test.com',
    'pixelwitch@test.com',
    'neonbrush@test.com'
);

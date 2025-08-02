---
applyTo: "**"
description: "Java + Spring Boot �J������ Copilot Agent �p�̋��ʃ��[��"
---

# �J���t���[���ʃ��[�� (Java + Spring Boot)

## ���� / �t���[�����[�N
- ����: Java 21+
- �t���[�����[�N: Spring Boot 3.x
- �r���h: Maven
- �e�X�g: JUnit5 (Mockito �ȂǊ����̃e�X�g���C�u�������g�p)

## �������[��
1. �R�[�h�X�^�C���̓v���W�F�N�g�� `checkstyle` / `formatter` �ݒ�ɏ]��
2. �ˑ������� **�R���X�g���N�^�C���W�F�N�V����** �������Ƃ���
3. �V�K�N���X�ɂ� Javadoc �܂��� JavaDoc�`���R�����g���L��
4. ��O������ `@ControllerAdvice` �����p���A�Ɩ���O�ƃV�X�e����O�����
5. �Z�L�����e�B�E�p�t�H�[�}���X�ɔz���iSQL�C���W�F�N�V�����΍�AN+1�N�G������Ȃǁj

## �e�X�g���[��
- **JUnit5** �ŕK���e�X�g��ǉ�
- �����e�X�g���󂳂Ȃ�����
- CI/CD ���Ő������邱�Ƃ��m�F

## PR�쐬���[��
- Draft PR ���쐬
- PR�{���ɂ͕K���ȉ����L��:
  - `Close #<ISSUE�ԍ�>`
  - �ύX���e�̊T�v
  - �ǉ������e�X�g
  - �m�F���Ăق����_�E���O�_

## �����`�F�b�N
- PR�쐬��� `./mvnw clean verify` �܂��� `./gradlew build` �����s
- Lint/Checkstyle�G���[��e�X�g���s������ΏC�����čĎ��s

## ����E�m�F
- �s���_��d�l�̞B����������΁A�K���R�����g�Ŋm�F���Ă���i�߂�

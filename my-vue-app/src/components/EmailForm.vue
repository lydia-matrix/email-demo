<template>
  <div class="email-form">
    <h2>이메일 보내기</h2>
    <form @submit.prevent="sendEmail">
      <div>
        <label>받는 사람</label>
        <input v-model="email.to" type="email" required />
      </div>
      <div>
        <label>제목</label>
        <input v-model="email.subject" type="text" required />
      </div>
      <div>
        <label>내용</label>
        <textarea v-model="email.content" required></textarea>
      </div>
      <button type="submit" :disabled="isSending">
        {{ isSending ? '전송 중...' : '보내기' }}
      </button>
    </form>
    <p v-if="statusMessage">{{ statusMessage }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      email: {
        to: '',
        subject: '',
        content: ''
      },
      statusMessage: '',
      isSending: false
    };
  },
  methods: {
    async sendEmail() {
      if (this.isSending) return;  // 이미 전송 중이면 중복 차단
      this.isSending = true;
      this.statusMessage = '';
      try {
        await axios.post('http://localhost:8080/api/email/send', this.email);
        this.statusMessage = '이메일이 성공적으로 전송되었습니다!';
        // 폼 초기화도 여기서 해도 됨
        // this.email.to = '';
        // this.email.subject = '';
        // this.email.content = '';
      } catch (error) {
        console.error(error);
        this.statusMessage = '이메일 전송 실패';
      } finally {
        this.isSending = false;
      }
    }
  }
};
</script>

<style scoped>
.email-form {
  max-width: 500px;
  margin: 30px auto;
}
.email-form input,
.email-form textarea {
  width: 100%;
  margin-bottom: 12px;
  padding: 8px;
}
button[disabled] {
  opacity: 0.6;
  cursor: not-allowed;
}
</style>

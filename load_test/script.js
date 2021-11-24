import http from 'k6/http';
import { sleep } from 'k6';
// export const options = {
//   vus: 10,
//   duration: '30s',
// };
const headers = {
  'Content-Type': 'application/json',
  Authorization: 'Bearer YqSdxNKHi0fkcNpuhsp6qCBff20',
  'Region-Id': 'k6',
};
export default function () {
  http.get(
    `http://localhost:8080/api/v1/apartment/TEST/questions?cursor=${Date.now()}&perPage=100`,
    { headers }
  );
  sleep(1);
}

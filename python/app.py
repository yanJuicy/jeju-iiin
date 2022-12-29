from flask import Flask, request, jsonify
from jeju_iiin_crawling import crawl
import json

with open('config.json', 'r') as f:
   config = json.loads(f.read())

ADMIN = config['ADMIN']

app = Flask(__name__)

## URL 별로 함수명이 같거나,
## route('/') 등의 주소가 같으면 안됩니다.

@app.route('/crawling', methods=['POST'])
def crawling():
   data = request.get_json()
   request_password = data['password']
   if request_password == ADMIN:
      crawling_start()
      return jsonify({'httpStatusCode': 200, 'msg': '크롤링 성공'})
   else:
      return jsonify({'httpStatusCode': 400, 'msg': '관리자 번호가 다릅니다.'})

   return jsonify({'httpStatusCode': 500, 'msg': '내부 오류입니다.'})


def crawling_start():
   # magazine, iiin
   crawl('http://iiinjeju.com/category/iiin/29/?page=1', 'MAGAZINE', 'IIIN', 1)
   # magazine, finders
   crawl('https://iiinjeju.com/category/finders/30/?page=1', 'MAGAZINE', 'FINDERS', 1)
   # shop, art
   crawl('https://iiinjeju.com/category/art/43/?page=1', 'SHOP', 'ART', 1)
   # shop, book
   crawl('https://iiinjeju.com/category/book/34/?page=1', 'SHOP', 'BOOK', 1)
   # shop, food
   crawl('https://iiinjeju.com/category/food/35/?page=1', 'SHOP', 'FOOD', 1)
   # shop, goods
   crawl('https://iiinjeju.com/category/goods/42/?page=1', 'SHOP', 'GOODS', 1)
   # shop, 한림수직
   crawl('https://iiinjeju.com/category/%ED%95%9C%EB%A6%BC%EC%88%98%EC%A7%81/51/?page=1', 'SHOP', 'HANLIMSUGIC', 1)
   # shop, reservation
   crawl('https://iiinjeju.com/category/reservation/53/?page=1', 'SHOP', 'GOODS', 1)
   return

if __name__ == '__main__':
   app.run('0.0.0.0', port=5000, debug=True)

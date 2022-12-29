import logging
import sys
import requests
import pymysql
from bs4 import BeautifulSoup
import json

with open('config.json', 'r') as f:
    config = json.loads(f.read())

# RDS info
host = config['RDS']['HOST']
database = config['RDS']['SCHEMA']
port = 3306
username = config['RDS']['USERNAME']
password = config['RDS']['PASSWORD']

def connection_RDS(host, port, database, username, password):
    try:
        conn = pymysql.connect(host=host, user=username, password=password, db=database, port=port, use_unicode=True, charset='utf8')
        cursor = conn.cursor()
    except:
        logging.error('RDS 연결 실패')
        sys.exit(1)
    return conn, cursor

def insert_RDS(name, caption, price, category, sub_category, thumbnail_img, detail_img):
    conn, cursor = connection_RDS(host, port, database, username, password)
    cursor.execute("INSERT INTO product (name, caption, price, category, sub_category, thumbnail_img_url, detail_img_url) VALUES (%s, %s, %s, %s, %s, %s, %s);", (name, caption, price, category, sub_category, thumbnail_img, detail_img))
    conn.commit()
    return

headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36'}


def crawl(category_url, category, sub_category, page):
    print('cur page', category_url)
    data = requests.get(category_url, headers=headers)
    soup = BeautifulSoup(data.text, 'html.parser')

    container = soup.select_one('ul.prdList')
    if container is None: # 데이터 없으면 종료
        return

    products = container.select('li > div')
    for product in products:
        # product caption
        caption = product.select_one('div.description li.summary').text

        # product detail page
        detail_url = product.select_one('div.prdImg > a')['href']
        detail_page = requests.get('http://iiinjeju.com' + detail_url, headers=headers)
        detail_soup = BeautifulSoup(detail_page.text, 'html.parser')

        # thumbnail img
        img = detail_soup.select_one('div.thumbnail > a > img')['src']
        if 'http' not in img:
            img = 'http:' + img

        # product name
        name = detail_soup.select_one('div.product_title').text.strip()
        idx = name.index('(')
        name = name[:idx]

        # product price
        price = detail_soup.select_one('#span_product_price_text').text
        idx = price.index('원')
        price = price[:idx].replace(',', '')

        # product detail_img_url
        detail_img_url = detail_soup.select_one('#prdDetail div.cont img')['ec-data-src']
        if 'http' not in detail_img_url:
            detail_img_url = 'http://iiinjeju.com' + detail_img_url

        print('category', category)
        print('subCategory', sub_category)
        print("name", name)
        print("thumbnail", img)
        print("caption", caption)
        print("price", price)
        print("detail_img", detail_img_url)
        print('DB 입력')
        insert_RDS(name, caption, price, category, sub_category, img, detail_img_url)
        print()


    # 다음 페이지 있으면 이동
    next_page = str(page + 1)
    idx = category_url.index('=')
    next_page_url = category_url[:idx + 1] + next_page
    crawl(next_page_url, category, sub_category, page + 1)


# magazine, iiin
# crawl('http://iiinjeju.com/category/iiin/29/?page=1', 'MAGAZINE', 'IIIN', 1)
# # magazine, finders
# crawl('https://iiinjeju.com/category/finders/30/?page=1', 'MAGAZINE', 'FINDERS', 1)
# # shop, art
# crawl('https://iiinjeju.com/category/art/43/?page=1', 'SHOP', 'ART', 1)
# # shop, book
# crawl('https://iiinjeju.com/category/book/34/?page=1', 'SHOP', 'BOOK', 1)
# # shop, food
# crawl('https://iiinjeju.com/category/food/35/?page=1', 'SHOP', 'FOOD', 1)
# # shop, goods
# crawl('https://iiinjeju.com/category/goods/42/?page=1', 'SHOP', 'GOODS', 1)
# # shop, 한림수직
# crawl('https://iiinjeju.com/category/%ED%95%9C%EB%A6%BC%EC%88%98%EC%A7%81/51/?page=1', 'SHOP', 'HANLIMSUGIC', 1)
# # shop, reservation
# crawl('https://iiinjeju.com/category/reservation/53/?page=1', 'SHOP', 'GOODS', 1)


# 크롤링 연습 코드
# data = requests.get('http://iiinjeju.com/category/iiin/29/', headers=headers)
# soup = BeautifulSoup(data.text, 'html.parser')
#
# ul = soup.select_one('ul.prdList')
#
# lis = ul.select('li > div')
# for li in lis:
#     a = li.select_one('div.prdImg > a')['href']
#     print(a)
#     caption = li.select_one('div.description li.summary').text
#     print(caption)
#
#     detail = requests.get('http://iiinjeju.com/' + a, headers=headers)
#     detail_soup = BeautifulSoup(detail.text, 'html.parser')
#     img = detail_soup.select_one('div.thumbnail > a > img')['src']
#     img = 'http:' + img
#     name = detail_soup.select_one('div.product_title').text.strip()
#     idx = name.index('(')
#     name = name[:idx]
#     price = detail_soup.select_one('#span_product_price_text').text
#     idx = price.index('원')
#     price = price[:idx].replace(',', '')
#     detail_img_url = detail_soup.select_one('#prdDetail div.cont img')['ec-data-src']
#     detail_img_url = 'http://iiinjeju.com' + detail_img_url
#
#     print(img)
#     print(name)
#     print(price)
#     print(detail_img_url)
#     print()
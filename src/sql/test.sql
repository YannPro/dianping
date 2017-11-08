select b.id,b.img_file_name,b.title,b.subtitle,b.price ,b.distance,b.number,b.`desc`,b.city,b.category,b.star_total_num,b.comment_total_num ,d_city.name city_name,d_category.name category_name
from business b left join dic d_city on b.city=d_city.code and d_city.type='city'
left join dic d_category on b.category=d_category.code and d_category.type='category' order by id limit 0,5
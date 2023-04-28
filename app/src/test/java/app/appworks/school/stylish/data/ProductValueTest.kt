package app.appworks.school.stylish.data

import app.appworks.school.stylish.network.moshi
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductValueTest {
    private val rawWomen = """
        {
          "data": [
            {
              "id": 201807201824,
              "category": "women",
              "title": "前開衩扭結洋裝",
              "description": "厚薄：薄\r\n彈性：無",
              "price": 799,
              "texture": "棉 100%",
              "wash": "手洗，溫水",
              "place": "中國",
              "note": "實品顏色依單品照為主",
              "story": "O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.",
              "main_image": "https://api.appworks-school.tw/assets/201807201824/main.jpg",
              "images": [
                "https://api.appworks-school.tw/assets/201807201824/0.jpg",
                "https://api.appworks-school.tw/assets/201807201824/1.jpg",
                "https://api.appworks-school.tw/assets/201807201824/0.jpg",
                "https://api.appworks-school.tw/assets/201807201824/1.jpg"
              ],
              "variants": [
                {
                  "color_code": "FFFFFF",
                  "size": "S",
                  "stock": 2
                },
                {
                  "color_code": "FFFFFF",
                  "size": "M",
                  "stock": 1
                },
                {
                  "color_code": "FFFFFF",
                  "size": "L",
                  "stock": 2
                },
                {
                  "color_code": "DDFFBB",
                  "size": "S",
                  "stock": 9
                },
                {
                  "color_code": "DDFFBB",
                  "size": "M",
                  "stock": 0
                },
                {
                  "color_code": "DDFFBB",
                  "size": "L",
                  "stock": 5
                },
                {
                  "color_code": "CCCCCC",
                  "size": "S",
                  "stock": 8
                },
                {
                  "color_code": "CCCCCC",
                  "size": "M",
                  "stock": 5
                },
                {
                  "color_code": "CCCCCC",
                  "size": "L",
                  "stock": 9
                }
              ],
              "colors": [
                {
                  "code": "FFFFFF",
                  "name": "白色"
                },
                {
                  "code": "DDFFBB",
                  "name": "亮綠"
                },
                {
                  "code": "CCCCCC",
                  "name": "淺灰"
                }
              ],
              "sizes": [
                "S",
                "M",
                "L"
              ]
            },
            {
              "id": 201807202140,
              "category": "women",
              "title": "透肌澎澎防曬襯衫",
              "description": "厚薄：薄\r\n彈性：無",
              "price": 599,
              "texture": "棉 100%",
              "wash": "手洗，溫水",
              "place": "中國",
              "note": "實品顏色依單品照為主",
              "story": "O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.",
              "main_image": "https://api.appworks-school.tw/assets/201807202140/main.jpg",
              "images": [
                "https://api.appworks-school.tw/assets/201807202140/0.jpg",
                "https://api.appworks-school.tw/assets/201807202140/1.jpg",
                "https://api.appworks-school.tw/assets/201807202140/0.jpg",
                "https://api.appworks-school.tw/assets/201807202140/1.jpg"
              ],
              "variants": [
                {
                  "color_code": "DDFFBB",
                  "size": "S",
                  "stock": 7
                },
                {
                  "color_code": "DDFFBB",
                  "size": "M",
                  "stock": 5
                },
                {
                  "color_code": "DDFFBB",
                  "size": "L",
                  "stock": 8
                },
                {
                  "color_code": "CCCCCC",
                  "size": "S",
                  "stock": 1
                },
                {
                  "color_code": "CCCCCC",
                  "size": "M",
                  "stock": 6
                },
                {
                  "color_code": "CCCCCC",
                  "size": "L",
                  "stock": 2
                }
              ],
              "colors": [
                {
                  "code": "DDFFBB",
                  "name": "亮綠"
                },
                {
                  "code": "CCCCCC",
                  "name": "淺灰"
                }
              ],
              "sizes": [
                "S",
                "M",
                "L"
              ]
            },
            {
              "id": 201807202150,
              "category": "women",
              "title": "小扇紋細織上衣",
              "description": "厚薄：薄\r\n彈性：無",
              "price": 599,
              "texture": "棉 100%",
              "wash": "手洗，溫水",
              "place": "中國",
              "note": "實品顏色依單品照為主",
              "story": "O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.",
              "main_image": "https://api.appworks-school.tw/assets/201807202150/main.jpg",
              "images": [
                "https://api.appworks-school.tw/assets/201807202150/0.jpg",
                "https://api.appworks-school.tw/assets/201807202150/1.jpg",
                "https://api.appworks-school.tw/assets/201807202150/0.jpg",
                "https://api.appworks-school.tw/assets/201807202150/1.jpg"
              ],
              "variants": [
                {
                  "color_code": "DDFFBB",
                  "size": "S",
                  "stock": 3
                },
                {
                  "color_code": "DDFFBB",
                  "size": "M",
                  "stock": 5
                },
                {
                  "color_code": "CCCCCC",
                  "size": "S",
                  "stock": 4
                },
                {
                  "color_code": "CCCCCC",
                  "size": "M",
                  "stock": 1
                },
                {
                  "color_code": "BB7744",
                  "size": "S",
                  "stock": 2
                },
                {
                  "color_code": "BB7744",
                  "size": "M",
                  "stock": 6
                }
              ],
              "colors": [
                {
                  "code": "DDFFBB",
                  "name": "亮綠"
                },
                {
                  "code": "CCCCCC",
                  "name": "淺灰"
                },
                {
                  "code": "BB7744",
                  "name": "淺棕"
                }
              ],
              "sizes": [
                "S",
                "M"
              ]
            },
            {
              "id": 201807202157,
              "category": "women",
              "title": "活力花紋長筒牛仔褲",
              "description": "厚薄：薄\r\n彈性：無",
              "price": 1299,
              "texture": "棉 100%",
              "wash": "手洗，溫水",
              "place": "中國",
              "note": "實品顏色依單品照為主",
              "story": "O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.",
              "main_image": "https://api.appworks-school.tw/assets/201807202157/main.jpg",
              "images": [
                "https://api.appworks-school.tw/assets/201807202157/0.jpg",
                "https://api.appworks-school.tw/assets/201807202157/1.jpg",
                "https://api.appworks-school.tw/assets/201807202157/0.jpg",
                "https://api.appworks-school.tw/assets/201807202157/1.jpg"
              ],
              "variants": [
                {
                  "color_code": "DDF0FF",
                  "size": "S",
                  "stock": 8
                },
                {
                  "color_code": "DDF0FF",
                  "size": "M",
                  "stock": 5
                },
                {
                  "color_code": "DDF0FF",
                  "size": "L",
                  "stock": 6
                },
                {
                  "color_code": "CCCCCC",
                  "size": "S",
                  "stock": 0
                },
                {
                  "color_code": "CCCCCC",
                  "size": "M",
                  "stock": 6
                },
                {
                  "color_code": "CCCCCC",
                  "size": "L",
                  "stock": 5
                },
                {
                  "color_code": "334455",
                  "size": "S",
                  "stock": 2
                },
                {
                  "color_code": "334455",
                  "size": "M",
                  "stock": 7
                },
                {
                  "color_code": "334455",
                  "size": "L",
                  "stock": 9
                }
              ],
              "colors": [
                {
                  "code": "DDF0FF",
                  "name": "淺藍"
                },
                {
                  "code": "CCCCCC",
                  "name": "淺灰"
                },
                {
                  "code": "334455",
                  "name": "深藍"
                }
              ],
              "sizes": [
                "S",
                "M",
                "L"
              ]
            },
            {
              "id": 201902191210,
              "category": "women",
              "title": "精緻扭結洋裝",
              "description": "厚薄：薄\r\n彈性：無",
              "price": 999,
              "texture": "棉 100%",
              "wash": "手洗",
              "place": "越南",
              "note": "實品顏色依單品照為主",
              "story": "O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.",
              "main_image": "https://api.appworks-school.tw/assets/201902191210/main.jpg",
              "images": [
                "https://api.appworks-school.tw/assets/201902191210/0.jpg",
                "https://api.appworks-school.tw/assets/201902191210/1.jpg",
                "https://api.appworks-school.tw/assets/201902191210/0.jpg",
                "https://api.appworks-school.tw/assets/201902191210/1.jpg"
              ],
              "variants": [
                {
                  "color_code": "FFFFFF",
                  "size": "S",
                  "stock": 0
                },
                {
                  "color_code": "FFFFFF",
                  "size": "M",
                  "stock": 9
                },
                {
                  "color_code": "FFDDDD",
                  "size": "S",
                  "stock": 2
                },
                {
                  "color_code": "FFDDDD",
                  "size": "M",
                  "stock": 1
                }
              ],
              "colors": [
                {
                  "code": "FFFFFF",
                  "name": "白色"
                },
                {
                  "code": "FFDDDD",
                  "name": "粉紅"
                }
              ],
              "sizes": [
                "S",
                "M"
              ]
            },
            {
              "id": 201902191242,
              "category": "women",
              "title": "透肌澎澎薄紗襯衫",
              "description": "厚薄：薄\r\n彈性：無",
              "price": 999,
              "texture": "棉 100%",
              "wash": "手洗",
              "place": "越南",
              "note": "實品顏色依單品照為主",
              "story": "O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.",
              "main_image": "https://api.appworks-school.tw/assets/201902191242/main.jpg",
              "images": [
                "https://api.appworks-school.tw/assets/201902191242/0.jpg",
                "https://api.appworks-school.tw/assets/201902191242/1.jpg",
                "https://api.appworks-school.tw/assets/201902191242/0.jpg",
                "https://api.appworks-school.tw/assets/201902191242/1.jpg"
              ],
              "variants": [
                {
                  "color_code": "DDFFBB",
                  "size": "M",
                  "stock": 3
                },
                {
                  "color_code": "DDFFBB",
                  "size": "L",
                  "stock": 9
                },
                {
                  "color_code": "DDF0FF",
                  "size": "M",
                  "stock": 2
                },
                {
                  "color_code": "DDF0FF",
                  "size": "L",
                  "stock": 6
                }
              ],
              "colors": [
                {
                  "code": "DDFFBB",
                  "name": "亮綠"
                },
                {
                  "code": "DDF0FF",
                  "name": "淺藍"
                }
              ],
              "sizes": [
                "M",
                "L"
              ]
            }
          ],
          "next_paging": 1
        }
    """.trimIndent()

    private val result = moshi.adapter(ProductListResult::class.java).fromJson(rawWomen)

    @Test
    fun test_hots_converter() {
        Assert.assertTrue(result != null)
        Assert.assertTrue(result!!.products != null)
        val list = result.products!!
        Assert.assertTrue(list.size == 6)
        assertEquals(41, list[0].stocks)
        assertEquals(29, list[1].stocks)
        assertEquals(21, list[2].stocks)
        assertEquals(48, list[3].stocks)
        assertEquals(12, list[4].stocks)
        assertEquals(20, list[5].stocks)


    }
}
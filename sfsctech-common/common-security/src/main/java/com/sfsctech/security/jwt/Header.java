package com.sfsctech.security.jwt;

import com.sfsctech.common.security.EncrypterTool.Security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.Date;

/**
 * Class header
 *
 * @author 张麒 2017/8/22.
 * @version Description:
 */
public class Header {

    Key key = MacProvider.generateKey();//这里是加密解密的key。

    String compactJws = Jwts.builder()//返回的字符串便是我们的jwt串了
            .setAudience("")// 接收方
            .setIssuer("") // 发行方
            .setIssuedAt(new Date()) // 发行时间

            .setSubject("Joe")//设置主题
            .signWith(SignatureAlgorithm.HS512, key)//设置算法（必须）
            .compact();//这个是全部设置完成后拼成jwt串的方法

    private String typ;

    private Security alg;

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public Security getAlg() {
        return alg;
    }

    public void setAlg(Security alg) {
        this.alg = alg;
    }
}
